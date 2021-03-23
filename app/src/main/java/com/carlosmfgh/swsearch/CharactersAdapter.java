package com.carlosmfgh.swsearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.MyViewHolder> {
    private static final String TAG = "CharactersAdapter";

    Context mContext;
    List<MyCharacter> mCharacterList;
    SharedPreferences shared_pref;

    public CharactersAdapter (Context context, List<MyCharacter> characterList) {

        mContext = context;

        mCharacterList = characterList;

        shared_pref = mContext.getApplicationContext().getSharedPreferences(Constants.SharedPref_favorites, 0);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.character_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String characterName = mCharacterList.get(position).getmName();

        holder.textViewName.setText(characterName);

        if (shared_pref.contains(characterName)) {
            holder.favorite.setVisibility(View.VISIBLE);
            holder.favorite_not.setVisibility(View.GONE);
        } else {
            holder.favorite.setVisibility(View.GONE);
            holder.favorite_not.setVisibility(View.VISIBLE);
        }

        holder.character_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(Constants.characterJsonObject, mCharacterList.get(position).getmJsonObject().toString());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mCharacterList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        ConstraintLayout character_row;
        ImageView favorite;
        ImageView favorite_not;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            character_row = itemView.findViewById(R.id.character_row);
            favorite = itemView.findViewById(R.id.favorite);
            favorite_not = itemView.findViewById(R.id.favorite_not);
        }
    }

    /**
     * Updates the recycler view with new list.
     * @param newListCharacters
     */
    public void updateData(List<MyCharacter> newListCharacters) {
        this.mCharacterList = newListCharacters;
        notifyDataSetChanged();
    }

}
