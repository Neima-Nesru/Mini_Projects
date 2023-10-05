import { Suspense } from 'react';
import {
  useRouteLoaderData,
  json,
  redirect,
  defer,
  Await,
} from 'react-router-dom';

import RecipeItem from '../components/RecipeItem';
import RecipesList from '../components/RecipesList';
import { getAuthToken } from '../util/auth';

function RecipeDetail() {
  const { recipe, recipes } = useRouteLoaderData('recipe-detail');

  return (
    <>
      <Suspense fallback={<p style={{ textAlign: 'center' }}>Loading...</p>}>
        <Await resolve={recipe}>
          {(loadedRecipe) => <RecipeItem recipe={loadedRecipe} />}
        </Await>
      </Suspense>
      <Suspense fallback={<p style={{ textAlign: 'center' }}>Loading...</p>}>
        <Await resolve={recipes}>
          {(loadedRecipes) => <RecipesList recipes={loadedRecipes} />}
        </Await>
      </Suspense>
    </>
  );
}

export default RecipeDetail;

async function loadRecipe(id) {
  const response = await fetch('http://localhost:8080/recipes/' + id);

  if (!response.ok) {
    throw json(
      { message: 'Could not fetch details for selected recipe.' },
      {
        status: 500,
      }
    );
  } else {
    const resData = await response.json();
    return resData.recipe;
  }
}

async function loadRecipes() {
  const response = await fetch('http://localhost:8080/recipes');

  if (!response.ok) {

    throw json(
      { message: 'Could not fetch recipes.' },
      {
        status: 500,
      }
    );
  } else {
    const resData = await response.json();
    return resData.recipes;
  }
}

export async function loader({ request, params }) {
  const id = params.recipeId;

  return defer({
    recipe: await loadRecipe(id),
    recipes: loadRecipes(),
  });
}

export async function action({ params, request }) {
  const recipeId = params.recipeId;

  const token = getAuthToken();
  const response = await fetch('http://localhost:8080/recipes/' + recipeId, {
    method: request.method,
    headers: {
      'Authorization': 'Bearer ' + token
    }
  });

  if (!response.ok) {
    throw json(
      { message: 'Could not delete recipe.' },
      {
        status: 500,
      }
    );
  }
  return redirect('/recipes');
}
