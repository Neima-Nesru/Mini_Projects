import { useRouteLoaderData } from 'react-router-dom';

import RecipeForm from '../components/RecipeForm';

function EditRecipe() {
  const data = useRouteLoaderData('recipe-detail');

  return <RecipeForm method="patch" recipe={data.recipe} />;
}

export default EditRecipe;
