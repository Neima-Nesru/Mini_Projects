// import { useLoaderData } from 'react-router-dom';
import { Link } from 'react-router-dom';

import classes from './RecipesList.module.css';

function RecipesList({ recipes }) {
  // const events = useLoaderData();

  return (
    <div className={classes.recipes}>
      <h2>All Recipes</h2>
      <ul className={classes.list}>
        {recipes.map((recipe) => (
          <li key={recipe.id} className={classes.item}>
            <Link to={`/recipes/${recipe.id}`}>
              <img src={recipe.image} alt={recipe.title} />
              <div className={classes.content}>
                <h2>{recipe.title}</h2>
                <time>{recipe.date}</time>
              </div>
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default RecipesList;
