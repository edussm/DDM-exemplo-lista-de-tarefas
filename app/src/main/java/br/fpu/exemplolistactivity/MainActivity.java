package br.fpu.exemplolistactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.fpu.exemplolistactivity.adapter.TarefaAdapter;
import br.fpu.exemplolistactivity.domain.Status;
import br.fpu.exemplolistactivity.domain.Tarefa;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener{

    private ListView listaDeTarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaDeTarefas = (ListView) findViewById(R.id.listView);
        listaDeTarefas.setOnItemClickListener(this);

        carregarListaDeTarefas();
    }

    public void carregarListaDeTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        for (int i = 0; i< 10; i++) {
            tarefas.add(new Tarefa(i, "Tarefa " + i, "Resp",
                    Status.FILA));
        }

        TarefaAdapter adapter = new TarefaAdapter(this, tarefas);

        listaDeTarefas.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view, int position, long id) {
        Toast.makeText(this, "Clicou na posicao "
                + position,Toast.LENGTH_LONG).show();
        Tarefa tarefa = (Tarefa) parent.getItemAtPosition(position);
        Log.d("CLICK", "ITEM: " + tarefa);
    }
}
