import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import EditRecipe from './pages/EditRecipe';
import ErrorPage from './pages/Error';
import RecipeDetail, {
  loader as recipeDetailLoader,
  action as deleteRecipeAction,
} from './pages/RecipeDetail';
import Recipes, { loader as recipesLoader } from './pages/Recipes';
import RecipesRootLayout from './pages/RecipesRoot';
import HomePage from './pages/Home';
import NewRecipe from './pages/NewRecipe';
import RootLayout from './pages/Root';
import { action as manipulateRecipeAction } from './components/RecipeForm';
import NewsletterPage, { action as newsletterAction } from './pages/Newsletter';
import AuthenticationPage, {
  action as authAction,
} from './pages/Authentication';
import { action as logoutAction } from './pages/Logout';
import { checkAuthLoader, tokenLoader } from './util/auth';

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    errorElement: <ErrorPage />,
    id: 'root',
    loader: tokenLoader,
    children: [
      { index: true, element: <HomePage /> },
      {
        path: 'recipes',
        element: <RecipesRootLayout />,
        children: [
          {
            index: true,
            element: <Recipes />,
            loader: recipesLoader,
          },
          {
            path: ':recipeId',
            id: 'recipe-detail',
            loader: recipeDetailLoader,
            children: [
              {
                index: true,
                element: <RecipeDetail />,
                action: deleteRecipeAction,
              },
              {
                path: 'edit',
                element: <EditRecipe />,
                action: manipulateRecipeAction,
                loader: checkAuthLoader,
              },
            ],
          },
          {
            path: 'new',
            element: <NewRecipe />,
            action: manipulateRecipeAction,
            loader: checkAuthLoader,
          },
        ],
      },
      {
        path: 'auth',
        element: <AuthenticationPage />,
        action: authAction,
      },
      {
        path: 'newsletter',
        element: <NewsletterPage />,
        action: newsletterAction,
      },
      {
        path: 'logout',
        action: logoutAction,
      },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
