package br.fpu.exemplolistactivity.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.fpu.exemplolistactivity.R;
import br.fpu.exemplolistactivity.domain.Tarefa;

public class TarefaAdapter extends ArrayAdapter<Tarefa> {
    public TarefaAdapter(Context context, List<Tarefa> tarefas) {
        super(context, 0, tarefas);
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





