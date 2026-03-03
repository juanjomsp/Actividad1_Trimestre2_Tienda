package com.example.tiendaapp; // Cambia esto si tu paquete se llama diferente

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCart;
    private TextView tvTotalPrice;
    private ProductAdapter adapter;
    private ArrayList<Product> cartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        // Recibir los datos del MainActivity
        if (getIntent().hasExtra("carrito")) {
            cartList = (ArrayList<Product>) getIntent().getSerializableExtra("carrito");
        }

        // Configurar RecyclerView reutilizando el Adapter
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(this, cartList, product -> {
            // En la pantalla del carrito no hacemos nada al pulsar el botón del producto
        });
        recyclerViewCart.setAdapter(adapter);

        updateTotalPrice();
    }

    // Calcular precio total
    private void updateTotalPrice() {
        double total = 0;
        for (Product p : cartList) {
            total += p.getPrice();
        }
        tvTotalPrice.setText(String.format("Total: %.2f €", total));
    }

    // Cargar el menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    // Acciones del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_confirm) {
            double total = 0;
            for (Product p : cartList) total += p.getPrice();

            // Mostrar Snackbar de confirmación
            Snackbar.make(findViewById(android.R.id.content),
                    String.format("Enhorabuena, compra por valor de %.2f realizada", total),
                    Snackbar.LENGTH_LONG).show();
            return true;

        } else if (id == R.id.action_empty) {
            // Vaciar lista y actualizar vista
            cartList.clear();
            adapter.notifyDataSetChanged();
            updateTotalPrice();

            // Mostrar Snackbar de vaciado
            Snackbar.make(findViewById(android.R.id.content),
                    "carrito vaciado",
                    Snackbar.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}