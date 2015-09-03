package com.example.jonathan.kookhut;

import android.provider.BaseColumns;

/**
 * Created by jonat on 2/09/2015.
 */
public class Contract {

    public interface CategoryColumns extends BaseColumns {
        public static final String name = "name";
        public static final String imgUrl = "imgUr";
    }

    public interface RecipeColumns extends BaseColumns {
        public static final String name = "name";
        public static final String imgUrl = "imgUrl";
        public static final String ingrediënts = "ingrediënts";
        public static final String preparation = "preparation";
    }

}
