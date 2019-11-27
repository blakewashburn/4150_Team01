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
    private Context context;

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
        context = getActivity();
        ArrayList<Meal> mealLog = Meal.createMealLog(context);

        // set up recycleview
        RecyclerView recyclerView = view.findViewById(R.id.meal_log_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // set up recycleview adapter
        MealAdapter adapter = new MealAdapter(mealLog);
        recyclerView.setAdapter(adapter);
        Log.d("ONCREATEVIEW", "We returned a view baby");
        return view;
    }   //end onCreateView method

    private class MealHolder extends RecyclerView.ViewHolder {
        private Meal currentMeal;
        private TextView mealDate;
        private ImageView mealPictured;
        private TextView mealType;
        private TextView impactScore;
        private int count = 0;

        public MealHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_meals, parent, false));
            Log.d("MEALHOLDER", "about to create a mealholder");
            mealPictured = itemView.findViewById(R.id.rv_meal_pictured);
            mealDate = itemView.findViewById(R.id.rv_list_meal_date);
            mealType = itemView.findViewById(R.id.rv_meal_type);
            impactScore = itemView.findViewById(R.id.rv_impact_score);
            Log.d("MEALHOLDER", "We created a mealholder yall");
        }   // end MealHolder constructor

        public void bind(Meal meal) {
            Log.d("BIND", "About to bind a meal");
            currentMeal = meal;
            // TODO: to.String(*with format*) for the below currentMeal.getDate()
            mealDate.setText(currentMeal.getDate());
            mealPictured.setImageDrawable(meal.getImage());
            mealType.setText(meal.getMealType());
            impactScore.setText(meal.getImpactScore());
            count++;
            Log.d("BIND", "We made it to count #:" + count);
        }   //end bind method
    }   //end MealHolder Class

    private class MealAdapter extends RecyclerView.Adapter<MealHolder> {
        private List<Meal> meals;

        public MealAdapter(List<Meal> myMeals) {
            meals = myMeals;
            Log.d("MEALADAPTER", "We created a mealAdapter boys");
        }   //end MealAdapter method

        @Override
        public MealHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d("CREATEVIEWHOLDER", "about to create that view holder");
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new MealHolder(layoutInflater, parent);
        }   //end onCreateViewHolder method

        @Override
        public void onBindViewHolder(MealHolder holder, int position) {
            Log.d("BindViewHolder", "about to bind a view holder");
            Meal meal = meals.get(position);
            holder.bind(meal);
            Log.d("BindViewHolder", "just bound that view holder");
        }   //end onBindViewHolder method

        @Override
        public int getItemCount() {
            Log.d("getItemCount", "asking for item count size, returning "+ meals.size());
            return meals.size();
        }   //end getItemCount method
    }   //end MealAdapter class
}   //end MealLogFragment Fragment
