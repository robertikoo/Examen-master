package net.sebaorrego.evaluacion2.views.activities

import android.content.pm.ActivityInfo
import java.util.Calendar
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.entities.Habilidad
import net.sebaorrego.evaluacion2.entities.Pokemon
import net.sebaorrego.evaluacion2.entities.Tipo
import net.sebaorrego.evaluacion2.services.ConexionSQL
import net.sebaorrego.evaluacion2.views.fragments.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


/*        private static final String TAG = "POKEDEX";

        private Retrofit retrofit;

        private RecyclerView recyclerView;
        private ListaPokemonAdapter listaPokemonAdapter;

        private int offset;

        private boolean aptoParaCargar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            listaPokemonAdapter = new ListaPokemonAdapter(this);
            recyclerView.setAdapter(listaPokemonAdapter);
            recyclerView.setHasFixedSize(true);
            final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if (dy > 0) {
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                        if (aptoParaCargar) {
                            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                Log.i(TAG, " Llegamos al final.");

                                aptoParaCargar = false;
                                offset += 20;
                                obtenerDatos(offset);
                            }
                        }
                    }
                }
            });


            retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

            aptoParaCargar = true;
            offset = 0;
            obtenerDatos(offset);
        }

        private void obtenerDatos(int offset) {
            PokeapiService service = retrofit.create(PokeapiService.class);
            Call<PokemonRespuesta> pokemonRespuestaCall = service.obtenerListaPokemon(20, offset);

            pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
                @Override
                public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                    aptoParaCargar = true;
                    if (response.isSuccessful()) {

                        PokemonRespuesta pokemonRespuesta = response.body();
                        ArrayList<Pokemon> listaPokemon = pokemonRespuesta.getResults();

                        listaPokemonAdapter.adicionarListaPokemon(listaPokemon);

                    } else {
                        Log.e(TAG, " onResponse: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                    aptoParaCargar = true;
                    Log.e(TAG, " onFailure: " + t.getMessage());
                }
            });



*/




        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        var conn = ConexionSQL(this, null, 1)
        if (conn.listarTipo().size>0 && conn.listarPokemon().size>0 ){
            fab.visibility = View.GONE
        }

        fab.setOnClickListener { view ->
            var tip1 = Tipo(1, "Agua", 1)
            var tip2 = Tipo(1, "Fuego", 1)
            var tip3 = Tipo(1, "Roca", 1)


            conn.insertarTipo(tip1)
            conn.insertarTipo(tip2)
            conn.insertarTipo(tip3)

            var conn = ConexionSQL(this, null, 1)
            var hab1 = Habilidad(1, "asd", 1)
            conn.insertarHabilidad(hab1)



            val c = Calendar.getInstance()
            val anio = c.get(Calendar.YEAR)
            val mes = c.get(Calendar.MONTH)
            val dia = c.get(Calendar.DAY_OF_MONTH)
            var m = mes + 1
            var fecha: String = "$dia/$m/$anio"

            var poke = Pokemon(1, "Luchetti", "98882992", fecha, 1)
            conn.insertarPokemon(poke)
            fab.visibility = View.GONE
            Snackbar.make(view, "Agregados datos de prueba ;)", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

            val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        var fm = supportFragmentManager
        var ft = fm.beginTransaction()
        var miFrag = FragmentPortada()
        ft.replace(R.id.fragLay,miFrag)
        ft.commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var fm = supportFragmentManager
        var ft = fm.beginTransaction()

        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_habilidades -> {
                var miFrag = FragmentHabilidades()
                miFrag.miContexto = this
                ft.replace(R.id.fragLay,miFrag)
                ft.commit()
            }

            R.id.nav_tipos -> {
                var miFrag = FragmentTipos()
                miFrag.miContexto = this
                ft.replace(R.id.fragLay,miFrag)
                ft.commit()
            }

            R.id.nav_pokemones -> {
                var miFrag = FragmentPokemones()
                miFrag.miContexto = this
                //miFrag.clientes = this.clientes
                ft.replace(R.id.fragLay,miFrag)
                ft.commit()
            }

            R.id.nav_listas -> {
                var miFrag = FragmentListas()
                miFrag.miContexto = this
                ft.replace(R.id.fragLay,miFrag)
                ft.commit()
            }

            R.id.nav_stock -> {
                var miFrag = FragmentStock()
                miFrag.miContexto = this
                ft.replace(R.id.fragLay,miFrag)
                ft.commit()
            }
            R.id.nav_salir -> {
                finish()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
