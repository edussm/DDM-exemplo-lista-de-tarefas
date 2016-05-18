package br.fpu.exemplolistactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import br.fpu.exemplolistactivity.adapter.TarefaAdapter;
import br.fpu.exemplolistactivity.adapter.TarefaBaseAdapter;
import br.fpu.exemplolistactivity.database.DBHandler;
import br.fpu.exemplolistactivity.domain.Status;
import br.fpu.exemplolistactivity.domain.Tarefa;
import br.fpu.exemplolistactivity.network.RestServiceClient;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener{
    private static final String TAG = "lista-de-tarefas";
    private ListView listaDeTarefas;
    private DBHandler dbHandler;
    private TarefaBaseAdapter adapter;
    private RestServiceClient restServiceClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        listaDeTarefas = (ListView) findViewById(R.id.listView);
        listaDeTarefas.setOnItemClickListener(this);
        registerForContextMenu(listaDeTarefas);

        dbHandler = new DBHandler(this);

        restServiceClient = new RestServiceClient(
                getResources().getString(R.string.web_service_root_url));

        adapter = new TarefaBaseAdapter(this, new ArrayList<Tarefa>());
        listaDeTarefas.setAdapter(adapter);

        reloadTarefas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listView) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;

            //Tarefa tarefa = (Tarefa) lv.getItemAtPosition(acmi.position);
            menu.setHeaderTitle(R.string.header_menu_tarefa);
            String[] menuItems = getResources().getStringArray(R.array.menu_tarefa);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                testarPostEPut();
                Intent intent = new Intent(this, CreateTarefaActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_refresh:
                reloadTarefas();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(TAG, item.toString());
        Log.d(TAG, "id: " + item.getItemId());

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        Tarefa tarefa = adapter.getItem(info.position);

        switch (item.getItemId()) {
            case 0: // Editar
                // TODO: enviar para a tela de editar com a tarefa
                return true;
            case 1: // Detalhar
                // TODO: criar uma tela para detalhar a tarefa
                return false;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view, int position, long id) {
        parent.showContextMenuForChild(view);
    }

    private void reloadTarefas() {
        adapter.getData().clear();
        restServiceClient.getTasks(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                adapter.getData().addAll((List<Tarefa>) data);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void testarPostEPut() {
        Tarefa t = new Tarefa(null, "post", "resp", Status.PENDENTE);
        restServiceClient.createTask(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                Log.d(TAG, "[POST] recebido: " + data);
                ((Tarefa) data).setDescricao("Alterado PUT");
                restServiceClient.updateTask(new Observer() {
                    @Override
                    public void update(Observable observable, Object data) {
                        Log.d(TAG, "[PUT] recebido: " + data);
                    }
                }, (Tarefa) data);
            }
        }, t);
    }
}
