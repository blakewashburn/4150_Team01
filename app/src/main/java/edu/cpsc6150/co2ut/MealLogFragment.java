package edu.cpsc6150.co2ut;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

public class MealLogFragment extends Fragment {

    // Required empty public constructor
    public MealLogFragment() {}

    public static MealLogFragment newInstance() {
        return new MealLogFragment();
    }   //end newInstance method

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }   //end onAttach method

    @Override
    public void onDetach() {
        super.onDetach();
    }   //end onDetach method

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Bring the meal log fragment to the view
        View view = inflater.inflate(R.layout.fragment_meal_log, container, false);
        ArrayList<Meal> mealLog = MealLogActivity.getMealLog();

        // set up recycleview
        RecyclerView recyclerView = view.findViewById(R.id.meal_log_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // set up recycleview adapter
        MealAdapter adapter = new MealAdapter(mealLog);
        recyclerView.setAdapter(adapter);
        return view;
    }   //end onCreateView method

    private class MealHolder extends RecyclerView.ViewHolder {
        private Meal currentMeal;
        private TextView mealDate;
        private ImageView mealPictured;
        private TextView mealType;
        private TextView impactScore;

        public MealHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_meals, parent, false));
            mealPictured = itemView.findViewById(R.id.rv_meal_pictured);
            mealDate = itemView.findViewById(R.id.rv_list_meal_date);
            mealType = itemView.findViewById(R.id.rv_meal_type);
            impactScore = itemView.findViewById(R.id.rv_impact_score);
        }   // end MealHolder constructor

        public void bind(Meal meal) {
            currentMeal = meal;
            mealDate.setText(currentMeal.getDate());
            mealPictured.setImageDrawable(meal.getImage());
            mealType.setText(meal.getMealType());
            impactScore.setText(meal.getImpactScore());
        }   //end bind method
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
