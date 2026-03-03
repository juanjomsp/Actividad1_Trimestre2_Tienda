package com.example.tiendaapp; // Cambia esto si tu paquete se llama diferente

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerCategories;
    private RecyclerView recyclerViewProducts;
    private ProductAdapter adapter;
    private ArrayList<Product> productList = new ArrayList<>();
    private ArrayList<Product> cartList = new ArrayList<>(); // Lista para el carrito [cite: 26, 39]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerCategories = findViewById(R.id.spinnerCategories);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        // Configurar el Adapter con la interfaz de Callback [cite: 8]
        adapter = new ProductAdapter(this, productList, product -> {
            cartList.add(product); // Añade el producto al carrito
            Toast.makeText(MainActivity.this, product.getTitle() + " añadido", Toast.LENGTH_SHORT).show();
        });
        recyclerViewProducts.setAdapter(adapter);

        loadCategories();
        loadProducts();
    }

    // Cargar menú superior [cite: 9, 34]
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Acción al pulsar "Ver carrito"
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_view_cart) {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("carrito", cartList); // Pasar datos mediante Intent con putExtra
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Petición Volley para categorías [cite: 36]
    private void loadCategories() {
        String url = "https://dummyjson.com/products/categories";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    ArrayList<String> categories = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject catObj = response.getJSONObject(i);
                            categories.add(catObj.getString("name"));
                        }
                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
                        spinnerCategories.setAdapter(spinnerAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error en categorías", Toast.LENGTH_SHORT).show());
        queue.add(request);
    }

    // Petición Volley para productos
    private void loadProducts() {
        String url = "https://dummyjson.com/products";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray productsArray = response.getJSONArray("products");
                        for (int i = 0; i < productsArray.length(); i++) {
                            JSONObject obj = productsArray.getJSONObject(i);
                            Product product = new Product(
                                    obj.getInt("id"),
                                    obj.getString("title"),
                                    obj.getDouble("price"),
                                    obj.getString("thumbnail")
                            );
                            productList.add(product);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Error en productos", Toast.LENGTH_SHORT).show());
        queue.add(request);
    }
}