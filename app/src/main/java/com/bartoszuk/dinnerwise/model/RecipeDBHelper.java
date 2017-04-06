package com.bartoszuk.dinnerwise.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bartoszuk.dinnerwise.R;

import java.util.Arrays;

/**
 * Created by Maria Bartoszuk on 10/03/2017.
 *
 * Database with all system recipes.
 */

public class RecipeDBHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 9;
    public static final String DATABASE_NAME = "Recipes.db";

    public RecipeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating the DB with all it's columns and their names.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Recipe.RecipeEntry.TABLE_NAME + " ("
                + Recipe.RecipeEntry._ID + " INTEGER PRIMARY KEY, "
                + Recipe.RecipeEntry.COLUMN_NAME_TITLE + " TEXT, "
                + Recipe.RecipeEntry.COLUMN_NAME_DESCRIPTION + " TEXT, "
                + Recipe.RecipeEntry.COLUMN_NAME_PREPARATION_TIME_MINS + " INTEGER, "
                + Recipe.RecipeEntry.COLUMN_NAME_SERVINGS + " INTEGER, "
                + Recipe.RecipeEntry.COLUMN_NAME_INGREDIENTS + " TEXT, "
                + Recipe.RecipeEntry.COLUMN_NAME_DIRECTIONS + " TEXT,"
                + Recipe.RecipeEntry.COLUMN_NAME_FAVOURITES + " INTEGER, "
                + Recipe.RecipeEntry.COLUMN_NAME_OWN + " INTEGER, "
                + Recipe.RecipeEntry.COLUMN_NAME_IMAGE_RES_ID + " INTEGER);");

        insert(db, R.drawable.aubergine_and_couscous_salad, "Aubergine & Couscous Salad", "A delicious and filling salad, without the salad in it. Great to take with you on the go or have at parties. Can also act as a side dish to a bigger meal.", 30, 2, "Cut the aubergine into 1cm slices. Place flat on a plate and sprinkle generously with salt. Let them “sweat” (sit and produce water) for 15 minutes. Next, rinse them and dry with a paper towel. Fry all pieces on a pan with 2 tbsp olive oil, for 5 - 7 minutes on each side until soft.\n" +
                        "\n" +
                        "Meanwhile place the couscous in a bowl and cover with boiling vegetable stock. Cover the bowl and leave it for 10 minutes. Cut the tomatoes, cucumbers and aubergines into small pieces and place in a bigger bowl. Sprinkle with lime juice, add fresh parsley, and 1 tbsp olive oil. Mix everything well. Sprinkle with salt and pepper to taste. Can be eaten hot or cool.\n",
                Ingredient.vegetable("aubergine", Quantity.fraction(1, 2)),
                Ingredient.spice("salt", Quantity.tbsp(2)),
                Ingredient.grain("couscous", Quantity.grams(50)),
                Ingredient.spice("vegetable stock cube", Quantity.fraction(1, 2)),
                Ingredient.spice("fresh parsely", Quantity.tbsp(10)),
                Ingredient.fruit("lime", Quantity.fraction(1, 2)),
                Ingredient.vegetable("tomato", Quantity.pieces(2)),
                Ingredient.vegetable("cucumber", Quantity.fraction(1, 3)),
                Ingredient.spice("olive oil", Quantity.tbsp(3)),
                Ingredient.spice("pepper", Quantity.tsp(1, 2)));

        insert(db, R.drawable.squash_based_pizza, "Squash-based Pizza", "A healthy twist on the regular pizza to try out something new. Customise it by adding your favourite toppings.", 90, 2,
                "First, prepare the squash for roasting. Cut the squash in half lengthwise and scoop out the seeds. Lay cut side up in a roasting pan and place in the oven. Cook in a 200 C for 40 minutes to 1 hour, or until a fork can easily pierce the flesh of the squash. Let cool, then scoop out the flesh and blend until smooth.\n" +
                        "Combine the 2 tbsp of ground flax seeds with 4 tbsp water. Leave it for 5 minutes to soak up the water.\n" +
                        "Combine the squash puree with the soaked flax seeds, almond flour, plain flour (or any flour of your choice), salt, pepper, dried oregano, and the additional ground flax. Stir to combine well. Spread the mixture onto a large rectangular tray with baking paper. Making sure that the edges are a little bit thicker to form the crust. Bake in a 220 C for 30 - 35 minutes or until the edges are crisp and golden and the centre is set.\n" +
                        "To make the pizza sauce, fry the onion on the hot olive oil until golden. Add the garlic and fry until the garlic turns golden (this happens quickly). Add the canned tomatoes, tomato concentrate and the vinegar. Stir and let it simmer for 15 minutes. Stir occasionally during that time.\n" +
                        "Top with pizza sauce, your chosen toppings, the additional olive oil and oregano, and bake for another 10 minutes.\n",
                Ingredient.vegetable("butternut squash", Quantity.fraction(2, 3)),
                Ingredient.nutsandseeds("almond flour", Quantity.grams(100)),
                Ingredient.grain("plain flour", Quantity.grams(90)),
                Ingredient.spice("salt", Quantity.tsp(1)),
                Ingredient.spice("pepper", Quantity.tsp(1, 2)),
                Ingredient.spice("oregano", Quantity.tsp(2)),
                Ingredient.nutsandseeds("flax seed", Quantity.tbsp(3)),
                Ingredient.vegetable("onion", Quantity.fraction(1, 2)),
                Ingredient.vegetable("garlic pieces", Quantity.pieces(2)),
                Ingredient.spice("olive oil", Quantity.tbsp(3)),
                Ingredient.vegetable("canned tomatoes", Quantity.grams(400)),
                Ingredient.spice("tomato concentrate", Quantity.tbsp(2)),
                Ingredient.spice("vinegar", Quantity.tbsp(1)));

        insert(db, R.drawable.veg_and_peanut_stirfry, "Veg and Peanut Stir-fry", "A fried vegetable mix in a sweet and spicy peanut sauce. Here, served on rice, but also tastes great on rice noodles.", 45, 2,
                "Begin by placing the rice in a pan with twice as much water as there is rice. Bring to boil and simmer for 15 - 20 minutes until soft. Chop the garlic and onion. Heat a pan with the coconut oil and place the onion in it. Cook until the onion turns golden, then add the garlic. In the meantime, wash and chop all the vegetables - peppers, beans and baby corn. Add the vegetables to the pan and fry until they become soft enough to eat. While the vegetables are cooking, make the sauce: place all the sauce ingredients in a cup with a few tablespoons of water and mix very well. When the vegetables are soft, add the sauce into the pan and stir everything well. To serve, you can either mix the rice into the vegetables or have them next to each other.\n",
                Ingredient.spice("coconut oil", Quantity.tbsp(3)),
                Ingredient.vegetable("garlic", Quantity.pieces(2)),
                Ingredient.vegetable("onion", Quantity.pieces(1)),
                Ingredient.vegetable("red pepper", Quantity.pieces(1)),
                Ingredient.vegetable("yellow pepper", Quantity.pieces(1)),
                Ingredient.vegetable("french beans", Quantity.grams(130)),
                Ingredient.vegetable("baby corn", Quantity.grams(175)),
                Ingredient.grain("rice", Quantity.grams(100)),
                Ingredient.nutsandseeds("peanut butter", Quantity.tbsp(1)),
                Ingredient.fruit("lime", Quantity.fraction(1, 4)),
                Ingredient.spice("soy sauce", Quantity.tbsp(1)),
                Ingredient.spice("grounded hot pepper", Quantity.tsp(1)),
                Ingredient.spice("grounded mild pepper", Quantity.tsp(1)),
                Ingredient.spice("ground garlic", Quantity.tsp(1)),
                Ingredient.spice("turmeric", Quantity.tsp(1, 2)));

        insert(db, R.drawable.cauliflower_soup, "Cauliflower Soup", "A creamy and hearty soup, that will fill you up with delicious veggies. Top up with a sour fruit, like pomegranate seeds, for the best taste.\n", 30, 3,
                "Begin by warming up the pan with olive oil. When hot, place the onion in the pan and fry until golden, stirring occasionally to not let it stick and burn. Dice the cauliflower and potatoes, and add them to the pan. Cover everything with a hot stock and let it boil. Cook for about 15 minutes until the vegetables and cooked and soft, yet firm enough to not fall apart. Blend everything well until it reaches a creamy consistency. Add the tahini paste and honey, and stir everything well. Add salt to taste.\n",
                Ingredient.spice("olive oil", Quantity.tbsp(2)),
                Ingredient.vegetable("onion", Quantity.pieces(1)),
                Ingredient.vegetable("cauliflower", Quantity.grams(600)),
                Ingredient.vegetable("potatoes", Quantity.pieces(2)),
                Ingredient.spice("vegetable stock cube", Quantity.pieces(1)),
                Ingredient.nutsandseeds("tahini paste", Quantity.tbsp(2)),
                Ingredient.spice("salt", Quantity.tsp(1)),
                Ingredient.spice("honey", Quantity.tsp(1)));

        insert(db, R.drawable.mini_pancakes_with_nuts, "Mini-pancakes with Nuts", "A slight-indulgent type of dinner composed of nutritious ingredients. Add your favourite toppings as preferred, we went for vanilla yoghurt and plum jam.\n", 30, 2,
                "Begin by mixing together both kinds of flour, sugar, salt and baking powder. In a separate dish, blend together the banana, almond milk (can be any other kind as well), and 2 tbsp coconut oil. Add the banana mixture to the dry ingredients and combine together well. Set aside for 10 minutes. Cut all nuts and mix with the hemp seeds. When the dough is ready, add the nut mixture in. Using a hot pan with 1 tbsp of coconut oil, fry 1 tbsp portions of dough to form mini-pancakes. Before every new batch, make sure to add 1 tbsp of coconut oil, so the pancakes do not stick to the pan.\n",
                Ingredient.grain("plain flour", Quantity.grams(50)),
                Ingredient.grain("wholemeal flour", Quantity.grams(100)),
                Ingredient.spice("brown sugar", Quantity.grams(20)),
                Ingredient.spice("salt", Quantity.tsp(1)),
                Ingredient.spice("baking powder", Quantity.tsp(1)),
                Ingredient.fruit("banana", Quantity.pieces(1)),
                Ingredient.nutsandseeds("almond milk", Quantity.grams(125)),
                Ingredient.spice("coconut oil", Quantity.tbsp(5)),
                Ingredient.nutsandseeds("almonds", Quantity.pieces(8)),
                Ingredient.nutsandseeds("macadamia nuts", Quantity.pieces(8)),
                Ingredient.nutsandseeds("hemp seeds", Quantity.tsp(1)));

        insert(db, R.drawable.very_green_smoothie, "Very Green Smoothie", "An on-the-go dinner alternative for those busy days when there is no time to cook. Very creamy and filling, and only take minutes to prepare.\n", 10, 1,
                "Wash the spinach well, peel the banana, kiwi, avocado and lime. Add all the ingredients in a big cup or bowl and blend until smooth. The smoothie can be topped up with any superfoods, nuts or seeds according to your preference.\n",
                Ingredient.vegetable("spinach", Quantity.grams(60)),
                Ingredient.fruit("banana", Quantity.pieces(1)),
                Ingredient.fruit("kiwi", Quantity.fraction(1, 2)),
                Ingredient.vegetable("avocado", Quantity.fraction(1, 4)),
                Ingredient.fruit("lime", Quantity.fraction(1, 4)));

        insert(db, R.drawable.sweet_potato_falafel, "Sweet Potato Falafels", "A good portion of baked falafels gives you a healthy and filling meal. Can be eaten with a salad, a soup, or just on its own as a snack.\n", 60, 2,
                "Begin by peeling, cutting, and steaming the sweet potato. Once it’s soft, blend until smooth. Put all the ingredients except the chickpeas and sesame seeds into a bowl, and mix together well. Add the chickpeas and blend until everything becomes smooth and is well mixed together. Place the sesame seeds on a place. \n" +
                        "\n" +
                        "Prepare a baking tray with a sheet of baking paper. Preheat the oven to 220 degrees C.\n" +
                        "\n" +
                        "Take 1 tablespoon of the falafel mixture, form a ball with your hands and roll it in the sesame seeds. Place it on the baking tray. Repeat making the falafel balls until all mixture is used up.\n" +
                        "\n" +
                        "Put the baking tray in the oven for 40 minutes, until the falafels start turning brown. Take out and leave to cool down.\n",
                Ingredient.vegetable("sweet potato", Quantity.pieces(1)),
                Ingredient.vegetable("garlic pieces", Quantity.pieces(2)),
                Ingredient.spice("olive oil", Quantity.tbsp(2)),
                Ingredient.spice("lemon", Quantity.fraction(1, 2)),
                Ingredient.spice("cumin", Quantity.tsp(1)),
                Ingredient.spice("mild pepper", Quantity.tsp(1)),
                Ingredient.spice("turmeric", Quantity.tsp(1, 2)),
                Ingredient.spice("apple cider vinegar", Quantity.tsp(1)),
                Ingredient.spice("tahini", Quantity.tbsp(1)),
                Ingredient.spice("salt", Quantity.tsp(1, 2)),
                Ingredient.spice("pepper", Quantity.tsp(1, 2)),
                Ingredient.grain("wholemeal flour", Quantity.tbsp(3)),
                Ingredient.grain("canned chickpeas", Quantity.grams(400)),
                Ingredient.nutsandseeds("sesame seeds", Quantity.grams(75)));

        insert(db, R.drawable.lentil_casserole, "Lentil Casserole", "A great mix of baked vegetables and lentils. Good to prepare before a busy week, when there will not be time to cook. Stores perfectly for a week.\n", 120, 4,
                "Start by cooking the potatoes. In the meantime begin peeling and chopping all the vegetables - carrots, celery stalks and onions. Cut the garlic into slices. Heat a big pan with the coconut oil. Add the carrots, celery and onions, and cook until the onion turns golden. Next mix in the turmeric and leave the vegetables to cook for 5 - 10 more minutes. Add the lentils and mix them in. Right away add the can of tomatoes. Now mix everything together very well. It’s also a good time to add a bit of salt and pepper to taste. Leave for 10 minutes for the sauce to reduce. Chop the parsley leaves and add it to the vegetables. Next place the vegetable mix in a dutch oven. Cover it on top with a layer of sliced garlic. Cut the cooked potatoes in thick slices, place them on top of the vegetables to form a layer, and season with salt, pepper and mild chilli powder. Cook in the oven for about 50 minutes in 190 degrees C.\n",
                Ingredient.vegetable("lentils", Quantity.grams(200)),
                Ingredient.vegetable("carrots", Quantity.pieces(3)),
                Ingredient.vegetable("celery stalks", Quantity.pieces(3)),
                Ingredient.vegetable("onions", Quantity.pieces(2)),
                Ingredient.spice("coconut oil", Quantity.tbsp(2)),
                Ingredient.spice("turmeric", Quantity.tsp(1, 2)),
                Ingredient.vegetable("can tomatoes", Quantity.pieces(1)),
                Ingredient.spice("vegetable stock cube", Quantity.pieces(1)),
                Ingredient.spice("fresh parsley", Quantity.tbsp(5)),
                Ingredient.vegetable("potatoes", Quantity.pieces(3)),
                Ingredient.vegetable("garlic pieces", Quantity.pieces(4)),
                Ingredient.nutsandseeds("mild chili", Quantity.tbsp(1)));

        insert(db, R.drawable.french_bean_pasta, "French Bean Pasta", "A green and whole meal take on an indulgent meal. The fresh basil makes the pesto very aromatic, making a feast for more senses.\n", 25, 2,
                "Begin by heating up about 3 glasses of water in a pan. When the water starts boiling, add the salt, bring to boil again and add the pasta. Leave to cook according to the time specification. In the meantime wash the french beans, cut off the ends and steam them until soft. The beans mix better with the pasta if the are in smaller pieces, so cut them up before or after cooking according to your judgement. Then, put the basil leaves, cashew nuts, olive oil and garlic in a blender and blend until it’s mostly smooth. Season with salt and pepper to taste. Mix the cooked pasta with the beans, then add pesto and mix again.\n",
                Ingredient.grain("whole wheat pasta", Quantity.grams(120)),
                Ingredient.vegetable("french green beans", Quantity.grams(180)),
                Ingredient.spice("salt", Quantity.tbsp(1)),
                Ingredient.vegetable("fresh basil", Quantity.grams(30)),
                Ingredient.nutsandseeds("cashew nuts", Quantity.grams(30)),
                Ingredient.vegetable("garlic piece", Quantity.pieces(1)),
                Ingredient.spice("olive oil", Quantity.tbsp(4)));

        insert(db, R.drawable.lazy_dumplings, "Lazy Dumplings", "Lazy Dumplings get their name from being very quick to prepare. They are in the sweet theme, but can be a great protein-rich meal.\n", 25, 2,
                "Mix the cheese with the egg and the pinch of salt. Make sure it is mixed well with no big clumps of cheese. Add in the flour and potato starch. Mix well again, by using clean hands to form a ball of dough. Split the dough into two parts. Shape each part into a long rectangle. Then cut it into pieces, about 1-finger sized. Boil a pan of water with a pinch of salt. Split the dumplings into 2 - 3 parts depending on the size of the pan. Add one portion of dumplings and boil for about 20 seconds from the moment they come up to the top. Repeat with the other portions.\n",
                Ingredient.dairy("cottage cheese", Quantity.grams(300)),
                Ingredient.dairy("egg", Quantity.pieces(1)),
                Ingredient.spice("salt", Quantity.tsp(1, 4)),
                Ingredient.grain("flour", Quantity.grams(50)),
                Ingredient.grain("potato starch", Quantity.tbsp(1)));

        insert(db, R.drawable.veggie_sushi, "Veggie Sushi", "Some think that sushi is difficult to make, but in fact it is very quick and only requires few simple steps. The veggies can be replaced with any desired filling.\n", 30, 2,
                "Place the rice in a pan and rinse a few times, until the water does not go cloudy anymore. Then add twice as much water to the pan, as there is rice and cook until soft and sticky. In the meantime, mix the rice vinegar with maple syrup and salt. When the rice is cooked, add the liquid mixture, mix in well and leave it to cool down. Wash all the vegetables and slice them in long strips. Place one piece of nori on a sushi mat. Put an semi-thick but even layer of rice on it, leaving the top inch empty. Place the vegetables in the long stripe in the middle of the rice. Roll it and put a few drops of water on the empty stripe of nori to help to seal the sushi roll. With a very sharp knife, cut the sushi roll into ½ inch slices. The sushi tastes the best with soy sauce and wasabi, but this can be adjusted according to personal preferences.\n",
                Ingredient.grain("basmati rice", Quantity.grams(190)),
                Ingredient.spice("rice vinegar", Quantity.tbsp(1)),
                Ingredient.spice("maple syrup", Quantity.tbsp(1)),
                Ingredient.spice("salt", Quantity.tsp(1)),
                Ingredient.vegetable("avocado", Quantity.fraction(1, 2)),
                Ingredient.vegetable("orange pepper", Quantity.fraction(1, 2)),
                Ingredient.vegetable("tomato", Quantity.pieces(1)),
                Ingredient.vegetable("cucumber", Quantity.fraction(1, 2)),
                Ingredient.vegetable("nori", Quantity.pieces(2)));

        insert(db, R.drawable.lecho, "Lecho", "Lecho is a traditional hungarian dish that is easy to make and customise to your taste. Can be served with any type of grains or rice.\n", 60, 4,
                "Begin by chopping the onion and garlic. Heat up a pan with coconut oil, add the onion and fry until golden, stirring occasionally. In the meantime dice the courgettes and peppers. When the onion turns golden, add in the garlic. Next, add the chopped vegetables, tomato passata and concentrate. Bring to boil, and simmer until all the vegetables are soft. Add in all the spices, customising to your taste. It is nice to experiment with your lecho. For example, if you want an option with meat, you can add a diced sausage to the onion at the beginning and fry them together.\n",
                Ingredient.spice("coconut oil", Quantity.tbsp(2)),
                Ingredient.vegetable("garlic pieces", Quantity.pieces(3)),
                Ingredient.vegetable("onion", Quantity.pieces(1)),
                Ingredient.vegetable("yellow pepper", Quantity.pieces(1)),
                Ingredient.vegetable("green pepper", Quantity.pieces(1)),
                Ingredient.vegetable("red pepper", Quantity.pieces(1)),
                Ingredient.vegetable("courgettes", Quantity.pieces(2)),
                Ingredient.spice("tomato concentrate", Quantity.tbsp(2)),
                Ingredient.vegetable("tomato passata", Quantity.grams(700)),
                Ingredient.spice("mild pepper", Quantity.tsp(1)),
                Ingredient.spice("hot pepper", Quantity.tsp(1)),
                Ingredient.spice("oregano", Quantity.tsp(1)),
                Ingredient.spice("coriander", Quantity.tsp(1, 2)),
                Ingredient.spice("basil", Quantity.tsp(1, 2)),
                Ingredient.spice("black pepper", Quantity.tsp(1)),
                Ingredient.spice("salt", Quantity.tsp(1)));

        insert(db, R.drawable.spinach_lasagne, "Spinach Lasagne", "Lasagne is a dish that can be made either on an indulgent side, or on a healthy side. Here is a healthy proposition of the spinach lasagne with soft ricotta cheese.", 50, 2,
                "Begin by washing the spinach and mushrooms very well. Heat up a pan and add the olive oil. Chop the garlic and also place it in the pan. Fry for 1 - 2 minutes until golden, but not burn. In the meantime, chop the mushrooms, then add them on the pan. After a few minutes add in the spinach and stir a bit. When the spinach is soft and has shrunk, add the oregano, salt, pepper and parmesan cheese, and mix every thing well. Lay out a baking tray, which is close to the lasagne sheet size. Grease it with some more olive oil. Place half of the spinach in the tray, cover it with a lasagne sheet. Place half of the ricotta cheese and again, cover it with a lasagne sheet. Add one more layer of the spinach, then lasagne sheet, and then ricotta. Place in an over, heated up to 180 degrees C, and cook for 35 minutes until the lasagne sheets are completely soft and cooked. After serving, you can add more parmesan cheese, if you wish.\n",
                Ingredient.spice("olive oil", Quantity.tbsp(2)),
                Ingredient.vegetable("garlic pieces", Quantity.pieces(2)),
                Ingredient.vegetable("mushrooms", Quantity.grams(250)),
                Ingredient.spice("oregano", Quantity.tsp(1)),
                Ingredient.spice("salt", Quantity.tsp(1, 2)),
                Ingredient.spice("black pepper", Quantity.tsp(1, 2)),
                Ingredient.vegetable("spinach", Quantity.grams(200)),
                Ingredient.dairy("ricotta cheese", Quantity.grams(250)),
                Ingredient.dairy("parmesan cheese", Quantity.tbsp(2)),
                Ingredient.grain("lasagne sheets", Quantity.pieces(4)));

        insert(db, R.drawable.french_omelette, "French Omelette", "A breakfast staple in many countries, can also serve a role of a dinner. The veggies inside can be replaced to whatever is in your fridge. Can also include ham and cheese.\n", 30, 2,
                "Wash all vegetables very well and chop them up. Heat up a large pan. When the pan is hot, add the coconut oil. Then put in the onion and fry until golden. Add in the garlic and cook for 1 minute. Then add the rest of the vegetables - peppers, tomato and spinach - and let it cook for about 10 minutes until all vegetables become softer. Stir occasionally, so they don’t stick to the pan. In a separate bowl fluff up the eggs with a fork, so the yolk and whites are well combined. Add salt and pepper to the eggs, and any other spices that you like. Pour the eggs over the vegetables on the pan and let it cook for about 10 - 15 minutes until is it well cooked underneath. Flip the omelette and cook it for another 5 - 10 minutes, until all of the egg is set.\n",
                Ingredient.spice("coconut oil", Quantity.tbsp(1)),
                Ingredient.vegetable("onion", Quantity.fraction(1, 2)),
                Ingredient.vegetable("garlic piece", Quantity.pieces(1)),
                Ingredient.vegetable("yellow pepper", Quantity.fraction(1, 2)),
                Ingredient.vegetable("green pepper", Quantity.fraction(1, 2)),
                Ingredient.vegetable("tomato", Quantity.pieces(1)),
                Ingredient.vegetable("spinach", Quantity.grams(50)),
                Ingredient.spice("salt", Quantity.tsp(1, 2)),
                Ingredient.spice("pepper", Quantity.tsp(1, 2)),
                Ingredient.dairy("eggs", Quantity.pieces(4)));

        insert(db, R.drawable.pickle_veggie_soup, "Pickle-Veggie Soup", "A light vegetable soup with a pickle twist, which makes the soup taste slightly sour. A traditional recipe in the western Europe. Can also be made on chicken broth base.\n", 60, 6,
                "Begin by preparing all the fresh vegetables - wash, peel, whatever applies to each vegetable. Pour the cold water into a large pot. Place the parsnip, onion, one carrot and the leak in the water. Bring to boil and keep boiling for 10 minutes. While the water is warming up, dice all the potatoes, slice the remaining carrot and shred all the pickles to use in a while. Add the salt, allspice, bay leaves, peppercorns and the stock cube and boil for another 5 minutes. Then add the potatoes and the carrot, bring to boil again and simmer for 30 minutes until the potatoes are soft. Add the pickles with chopped dill and parsley into the soup, bring to boil again and simmer for 10 minutes.\n",
                Ingredient.vegetable("parsnip", Quantity.pieces(1)),
                Ingredient.vegetable("carrots", Quantity.pieces(2)),
                Ingredient.vegetable("onion", Quantity.pieces(1)),
                Ingredient.vegetable("leek", Quantity.pieces(1)),
                Ingredient.vegetable("potatoes", Quantity.pieces(4)),
                Ingredient.vegetable("pickles", Quantity.grams(400)),
                Ingredient.spice("salt", Quantity.tsp(1)),
                Ingredient.spice("allspice", Quantity.tsp(1)),
                Ingredient.spice("bay leaves", Quantity.pieces(2)),
                Ingredient.spice("peppercorns", Quantity.pieces(5)),
                Ingredient.spice("vegetable stock cube", Quantity.pieces(1)),
                Ingredient.spice("fresh dill", Quantity.tbsp(5)),
                Ingredient.spice("fresh parsley", Quantity.tbsp(5)));

        insert(db, R.drawable.aubergine_dip, "Aubergine Dip", "A very aromatic and creamy dip, which is great to eat in a sandwich or tortilla, or mixed in with any type of grains and rice.", 90, 4,
                "Begin by washing the aubergines and pricking them in a few spots with a knife. Place them in a baking tray and bake for 40 minutes in 220C. Wrap a whole bulb of garlic in a piece of foil, drizzle with olive oil and close it. Put it in the oven for 30 minutes alongside the aubergines. When the aubergines are soft inside, take them and the garlic out of the oven. Cut the aubergines sideways and leave everything to cool. When everything is cool already scoop out the aubergines and squeeze out the garlic pieces from their shells. Place everything in a blender, add all the other ingredients and blend into a paste. It is ok to have smaller pieces unblended if you want to keep some more texture to it.\n",
                Ingredient.vegetable("aubergines", Quantity.pieces(3)),
                Ingredient.vegetable("garlic bulb", Quantity.pieces(1)),
                Ingredient.spice("olive oil", Quantity.tbsp(3)),
                Ingredient.fruit("lemon", Quantity.fraction(1, 2)),
                Ingredient.spice("tahini", Quantity.tbsp(1)),
                Ingredient.spice("paprika", Quantity.tsp(2)),
                Ingredient.spice("cumin", Quantity.tsp(1, 2)),
                Ingredient.spice("salt", Quantity.tsp(1, 2)),
                Ingredient.spice("pepper", Quantity.tsp(1, 2)));

        db.execSQL("CREATE TABLE " + GroceryList.RecipeEntry.TABLE_NAME + " ("
                + GroceryList.RecipeEntry._ID + " INTEGER PRIMARY KEY, "
                + GroceryList.RecipeEntry.COLUMN_NAME_RECIPE_ID + " INTEGER, "
                + GroceryList.RecipeEntry.COLUMN_NAME_DAY_OF_WEEK + " INTEGER, "
                + GroceryList.RecipeEntry.COLUMN_NAME_DISCARDED + " INTEGER, "
                + GroceryList.RecipeEntry.COLUMN_NAME_CHECKED_INGREDIENTS + " TEXT);");
    }

    // Delete the previous table and add an updated one.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over.
        db.execSQL("DROP TABLE IF EXISTS " + Recipe.RecipeEntry.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + GroceryList.RecipeEntry.TABLE_NAME + ";");
        onCreate(db);
    }

    // Ensuring that any change in version is an upgrade of DB.
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void insert(SQLiteDatabase db, int imageResId, String title, String description,
                        int preparationTimeMin, int servings, String directions,
                        Ingredient... ingredients) {
        Recipe recipe = new Recipe();
        recipe.setImageResId(imageResId);
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setPreparationTimeInMinutes(preparationTimeMin);
        recipe.setNumberOfServings(servings);
        recipe.setIngredients(Arrays.asList(ingredients));
        recipe.setDirections(directions);
        ContentValues values = RecipesDB.valuesOf(recipe);
        db.insert(Recipe.RecipeEntry.TABLE_NAME, null, values);
    }
}
