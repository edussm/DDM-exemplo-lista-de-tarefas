package br.fpu.exemplolistactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import br.fpu.exemplolistactivity.adapter.TarefaAdapter;
import br.fpu.exemplolistactivity.adapter.TarefaBaseAdapter;
import br.fpu.exemplolistactivity.database.DBHandler;
import br.fpu.exemplolistactivity.domain.Status;
import br.fpu.exemplolistactivity.domain.Tarefa;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener{

    private ListView listaDeTarefas;
    private DBHandler dbHandler;
    private TarefaBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaDeTarefas = (ListView) findViewById(R.id.listView);
        listaDeTarefas.setOnItemClickListener(this);
        dbHandler = new DBHandler(this);

        carregarListaDeTarefas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                Tarefa t = new Tarefa();
                t.setDescricao("Teste " + new Date());
                t.setStatus(Status.FILA);
                dbHandler.addTarefa(t);
            case R.id.action_refresh:
                reloadTarefas();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void reloadTarefas() {
        adapter.getData().clear();
        adapter.getData().addAll(dbHandler.getAllTarefas());
        adapter.notifyDataSetChanged();
    }

    public void carregarListaDeTarefasAntigo() {
        List<Tarefa> tarefas = new ArrayList<>();
        for (int i = 0; i< 10; i++) {
            tarefas.add(new Tarefa(i, "Tarefa " + i, "Resp",
                    Status.FILA));
        }

        TarefaAdapter adapter = new TarefaAdapter(this, tarefas);
        listaDeTarefas.setAdapter(adapter);
    }

    public void carregarListaDeTarefas() {
        List<Tarefa> tarefas = dbHandler.getAllTarefas();
        adapter = new TarefaBaseAdapter(this, tarefas);
        listaDeTarefas.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view, int position, long id) {
        Toast.makeText(this, "Clicou na posicao "
                + position,Toast.LENGTH_LONG).show();
        Tarefa tarefa = (Tarefa) parent.getItemAtPosition(position);
        dbHandler.deleteTarefa(tarefa);
        reloadTarefas();
        Log.d("CLICK", "ITEM: " + tarefa);
    }
}
