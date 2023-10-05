import { NavLink, useRouteLoaderData } from 'react-router-dom';

import classes from './RecipesNavigation.module.css';

function RecipesNavigation() {
  // const token = useRouteLoaderData('root');

  return (
    <header className={classes.header}>
      <h1>Recipes</h1>
    </header>
  );
}

export default RecipesNavigation;
