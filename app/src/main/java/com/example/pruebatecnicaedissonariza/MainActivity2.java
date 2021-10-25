package com.example.pruebatecnicaedissonariza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity2 extends AppCompatActivity {
    TableLayout tabla;
    TextView tcodigo,tsustituto,tnid,ttipo,tcategoria1,tcategoria2,tcategoria3,tdescripcion,t1,t2,t3,t4,t5,p1,p2,p3,p4,p5;
    String token;
    String uid;
    String codigo,codigoselec,codigoFinal,nid,nid_su,sustituto,tipo,categoria1,categoria2,categoria3,descripcion,precios;
    private RequestQueue rq;
    ListView lista;
    String listaElementos [] ={"", "", "", "", "", "", "", "","",""};
    String valor [] ={"", "", "", "", "", "", "", "","","", "", "", "", "", "", "", "",""};
    String titulo[] ={"", "", "", "", "", "", "", "","","", "", "", "", "", "", "", "",""};
    List<String> listaString;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rq = Volley.newRequestQueue(this);
        tcodigo = findViewById(R.id.tcodigo);
        tnid = findViewById(R.id.tnid);
        tsustituto = findViewById(R.id.tsustituto);
        ttipo = findViewById(R.id.ttipo);
        tcategoria1 = findViewById(R.id.tcategoria1);
        tcategoria2 = findViewById(R.id.tcategoria2);
        tcategoria3 = findViewById(R.id.tcategoria3);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.t5);
        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
        p3 = findViewById(R.id.p3);
        p4 = findViewById(R.id.p4);
        p5 = findViewById(R.id.p5);
        tdescripcion=findViewById(R.id.tdescripcion);
        tabla=(TableLayout)findViewById(R.id.tabla);
        lista = findViewById(R.id.lista);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listaElementos);
       lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               codigoselec =listaElementos[position];
               lista.setVisibility(View.INVISIBLE);
               tabla.setVisibility(View.VISIBLE);
               recibirRespuesta();
               resultado();

           }
       });
    }

    private TextView findViewById(TableLayout tabla) {
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_activity_main2,menu);
       final MenuItem searchItem = menu.findItem(R.id.buscar);
       final SearchView searchView= (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                lista.setVisibility(View.VISIBLE);
                tabla.setVisibility(View.INVISIBLE);
                codigo=newText;
                consulta();
            return true;
            }
        });
        return true;
    }
    public void consulta() {
        String url = "https://makita.com.co/ventas/consulta_referencia_ventas_app?codigo="+codigo;
        StringRequest postResquest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray arrayObject = (JSONArray) object.get("results");
                        for (int i = 0; i < 10; i++) {
                            JSONObject object2 = (JSONObject) arrayObject.get(i);
                            listaElementos [i]= object2.get("codigo").toString();
                        }
                        listaString =new ArrayList<>(Arrays.asList(listaElementos));
                        lista.setAdapter(arrayAdapter);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postResquest);
    }
    private void recibirRespuesta(){
        Bundle extras = getIntent().getExtras();
        token = extras.getString("token");
        uid = extras.getString("uid");
    }
    private void resultado() {
        String url = "https://makita.com.co/ventas/consulta_detalle_referencia_ventas_app";
        StringRequest postResquest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    codigoFinal= object.getString("codigo");
                    nid        = object.getString( "nid");
                    nid_su     = object.getString( "nid_su");
                    sustituto  = object.getString( "sustituto");
                    tipo       = object.getString( "tipo");
                    categoria1 = object.getString( "categoria1");
                    categoria2 = object.getString( "categoria2");
                    categoria3 = object.getString( "categoria3");
                    descripcion= object.getString( "descripcion");
                    precios    = object.getString( "precios");
                    JSONArray arrayObject = (JSONArray) object.get("precios");
                    String DENE="DENEGADO";
                    int k = 0;
                    for (int i = 0; i < arrayObject.length(); i++) {
                        JSONObject object2 = (JSONObject) arrayObject.get(i);
                        String DEN=object2.get("valor").toString();
                        if (DEN.equals(DENE)){
                        }else {
                           k++;
                            valor[k] = object2.get("valor").toString();
                            titulo[k] = object2.get("titulo").toString();
                        }
                    }
                    LLenarText();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("codigo", codigoselec);
                params.put("uid", uid);
                params.put("token", token);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postResquest);
    }
public void LLenarText(){
            tcodigo.setText(codigoFinal);
            tnid.setText(nid+ nid_su);
            tsustituto.setText(sustituto);
            ttipo.setText(tipo);
            tcategoria1.setText(categoria1);
            tcategoria2.setText(categoria2);
            tcategoria3.setText(categoria3);
            tdescripcion.setText(descripcion);
               t1.setText(titulo[0]);
               t2.setText(titulo[1]);
               t3.setText(titulo[2]);
               t4.setText(titulo[3]);
               t5.setText(titulo[4]);
               p1.setText(valor[0]);
               p2.setText(valor[1]);
               p3.setText(valor[2]);
               p4.setText(valor[3]);
               p5.setText(valor[4]);

}
}

