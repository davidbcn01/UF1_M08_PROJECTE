package com.example.futinfov2;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.futinfov2.databinding.FragmentJugadoresValiososBinding;
import com.example.futinfov2.databinding.ViewholderJugadorBinding;
import com.example.futinfov2.databinding.ViewholderJugadoresBinding;

import java.util.List;


public class JugadoresValiososFragment extends Fragment {



    private FragmentJugadoresValiososBinding binding;
    private NavController navController;
    private ListaJugadoresViewModel listaJugadoresViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentJugadoresValiososBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(view);

        listaJugadoresViewModel = new ViewModelProvider(requireActivity()).get(ListaJugadoresViewModel.class);


        binding.irAInsertarAlbum.setOnClickListener(v -> {
            navController.navigate(R.id.action_jugadoresValiososFragment_to_insertarJugadoresFragment);

        });

        JugadoresAdapter jugadoresAdapter = new JugadoresAdapter();
        binding.recyclerView.setAdapter(jugadoresAdapter);

        listaJugadoresViewModel.obtener().observe(getViewLifecycleOwner(), jugadors -> {
            jugadoresAdapter.establecerJugadoresList(jugadors);
        });

    }

    class JugadoresAdapter extends RecyclerView.Adapter<JugadorViewHolder>{

        List<Jugador> JugadorList;

        @NonNull
        @Override
        public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return  new JugadorViewHolder(ViewholderJugadoresBinding.inflate(getLayoutInflater(), parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull JugadorViewHolder holder, int position) {
            Jugador jugador = JugadorList.get(position);
            holder.binding.nombre.setText(jugador.nombre);
            holder.binding.equipo.setText(jugador.equipo);

            Glide.with(holder.itemView).load(jugador.imagen).into(holder.binding.carta);
        }

        @Override
        public int getItemCount() {
            return JugadorList == null ? 0 : JugadorList.size();
        }

        void establecerJugadoresList(List<Jugador> JugadorList){
            this.JugadorList = JugadorList;
            notifyDataSetChanged();
        }



    }



    class JugadorViewHolder extends RecyclerView.ViewHolder{
        ViewholderJugadoresBinding binding;
        public JugadorViewHolder(@NonNull ViewholderJugadoresBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}


