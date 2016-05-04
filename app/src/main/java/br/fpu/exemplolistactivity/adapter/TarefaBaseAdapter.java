package br.fpu.exemplolistactivity.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.fpu.exemplolistactivity.R;
import br.fpu.exemplolistactivity.domain.Tarefa;

public class TarefaBaseAdapter extends BaseAdapter {

    private List<Tarefa> tarefas;
    private Context context;

    public TarefaBaseAdapter(Context context, final List<Tarefa> tarefas) {
        this.tarefas = tarefas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return tarefas.size();
    }

    @Override
    public Tarefa getItem(int position) {
        return tarefas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tarefas.get(position).getId();
    }

    public List<Tarefa> getData() {
        return tarefas;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        // Pegar o item na posicao atual
        Tarefa tarefa = getItem(position);

        // Se a view não estiver sendo usada, instanciamos uma nova
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_lista, parent, false);
        }

        // Preencher os dados do item
        // Pegando os objetos
        TextView id = (TextView)
                convertView.findViewById(R.id.textViewIdTarefa);
        TextView descricao = (TextView)
                convertView.findViewById(R.id.textViewDescTarefa);

        // Populando os dados
        id.setText(String.valueOf(tarefa.getId()));
        descricao.setText(tarefa.getDescricao());

        // Retornar o item da lista
        return convertView;
    }
}





