/**
 * Team Name: Team_01
 * Team Member1 Name: Blake Washburn
 * Team Member1 CUID: C89257841
 * Team Member1 email: bwashbu@g.clemson.edu
 *
 * Team Member2: Stephen Carvalho
 * Team Member2 CUID: C70675411
 * Team Member2 email: scarval@g.clemson.edu
 *
 * Citations:
 *
 * App Icon: https://www.flaticon.com/home
 *
 * States Page Icons: https://www.flaticon.com/home
 *
 * Detect Activity Code Inspired from: https://developers.google.com/location-context/activity-recognition
 *
 * Meal Log Code Inspired from: https://learn.zybooks.com/zybook/CLEMSONCPSC4150PlaueFall2019/chapter/5/section/2
 * and https://learn.zybooks.com/zybook/CLEMSONCPSC4150PlaueFall2019/chapter/9/section/5
 *
 * SQLite Database Code Inspired from: https://developer.android.com/training/data-storage/sqlite
 * and https://learn.zybooks.com/zybook/CLEMSONCPSC4150PlaueFall2019/chapter/6/section/4
 *
 * FusedLocation Provider and Location Services code inspired from: https://learn.zybooks.com/zybook/CLEMSONCPSC4150PlaueFall2019/chapter/9/section/6
 * and https://developer.android.com/training/location
 *
 * Carbon Footprint Calculator Metrics: https://www.epa.gov/energy/greenhouse-gases-equivalencies-calculator-calculations-and-references
 */
package edu.cpsc6150.co2ut;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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

    /**
     * Functionality: necessary empty fragment constructor
     * PreConditions: none
     * PostConditions: none
     */
    public MealLogFragment() {} //end MealLogFragment constructor

    /**
     * Functionality: attach item to context
     * PreConditions: context cannot be null
     * PostConditions: context is attached by calling super.onAttach
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }   //end onAttach method

    /**
     * Functionality: detach item from context
     * PreConditions: none
     * PostConditions: item is detached by calling super.onDetach
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }   //end onDetach method

    /**
     * Functionality: setup recycler view and get mealLog to populate recyclerview
     * PreConditions: inflater && container cannot be null
     * PostConditions: MealLogFragment displays a scrollable list of meals that the user can
     *                  interact with
     */
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

        /**
         * Functionality: constructor for the MealHolder class
         * PreConditions: inflater and parent cannot be null
         * PostConditions: MealHolder is initialized
         */
        public MealHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_meals, parent, false));
            mealPictured = itemView.findViewById(R.id.rv_meal_pictured);
            mealDate = itemView.findViewById(R.id.rv_list_meal_date);
            mealType = itemView.findViewById(R.id.rv_meal_type);
            impactScore = itemView.findViewById(R.id.rv_impact_score);
        }   // end MealHolder constructor

        /**
         * Functionality: Updates the local currentMeal variable to the passed in meal variable
         * PreConditions: meal must be completely initialized
         * PostConditions: the local currentMeal = passed meal variable
         */
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

        /**
         * Functionality: constructor for MealAdapter class
         * PreConditions: myMeals cannot be null
         * PostConditions: this.meals = passed myMeals variable
         */
        public MealAdapter(List<Meal> myMeals) {
            meals = myMeals;
        }   //end MealAdapter method

        /**
         * Functionality: inflate layout and call MealHolder constructor
         * PreConditions: parent && viewType cannot be null
         * PostConditions: a valid MealHolder object is returned
         */
        @Override
        public MealHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new MealHolder(layoutInflater, parent);
        }   //end onCreateViewHolder method

        /**
         * Functionality: Call bind to connect the holder to the meal
         * PreConditions: holder && position cannot be null
         * PostConditions: holder.bind() will be called with a valid Meal object
         */
        @Override
        public void onBindViewHolder(MealHolder holder, int position) {
            Meal meal = meals.get(position);
            holder.bind(meal);
        }   //end onBindViewHolder method

        /**
         * Functionality: Returns the number of meals in the recyclerview
         * PreConditions: this.meals should be initialized
         * PostConditions: getItemCount() = meals.size()
         */
        @Override
        public int getItemCount() {
            return meals.size();
        }   //end getItemCount method
    }   //end MealAdapter class
}   //end MealLogFragment Fragment
