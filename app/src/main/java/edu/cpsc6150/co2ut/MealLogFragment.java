package edu.cpsc6150.co2ut;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

public class MealLogFragment extends Fragment {
    private onMealSelectedListener listener;

    // Required empty public constructor
    public MealLogFragment() {}

    public static MealLogFragment newInstance() {
        return new MealLogFragment();
    }   //end newInstance method

    public interface onMealSelectedListener {
        void onMealSelected(Meal meal);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onMealSelectedListener) {
            listener = (onMealSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onMealSelectedListener");
        }   //end if-else
    }   //end onAttach method

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }   //end onDetach method


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_meal_log, container, false);
        ArrayList<Meal> mealLog = Meal.createMealLog();

        // set up recycleview
        RecyclerView recyclerView = view.findViewById(R.id.meal_log_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // set up recycleview adapter
        MealAdapter adapter = new MealAdapter(mealLog);
        recyclerView.setAdapter(adapter);
        return view;
    }   //end onCreateView

    private class MealHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Meal currentMeal;
        private TextView mealSelected;

        public MealHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_meals, parent, false));
            itemView.setOnClickListener(this);
            mealSelected = itemView.findViewById(R.id.list_meal_date);
        }   // end CityHolder constructor

        public void bind(Meal meal) {
            currentMeal = meal;
            // TODO: to.String(*with format*) for the below currentMeal.getDate()
            mealSelected.setText(currentMeal.getDate());
        }   //end bind method

        @Override
        public void onClick(View view) {
            listener.onMealSelected(currentMeal);
        }   //end onClick method
    }   //end MealHolder Class

    private class MealAdapter extends RecyclerView.Adapter<MealHolder> {
        private List<Meal> meals;

        public MealAdapter(List<Meal> myMeals) {
            meals = myMeals;
        }   //end MealAdapter method

        @Override
        public MealHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new MealHolder(layoutInflater, parent);
        }   //end onCreateViewHolder method

        @Override
        public void onBindViewHolder(MealHolder holder, int position) {
            Meal meal = meals.get(position);
            holder.bind(meal);
        }   //end onBindViewHolder method

        @Override
        public int getItemCount() {
            return meals.size();
        }   //end getItemCount method
    }   //end MealAdapter class
}   //end MealLogFragment Fragment
