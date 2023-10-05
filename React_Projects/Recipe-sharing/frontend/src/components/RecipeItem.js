import { Link, useRouteLoaderData, useSubmit } from 'react-router-dom';

import classes from './RecipeItem.module.css';

function RecipeItem({ recipe }) {
  const token = useRouteLoaderData('root');
  const submit = useSubmit();

  function startDeleteHandler() {
    const proceed = window.confirm('Are you sure you want to delete this Recipe?');

    if (proceed) {
      submit(null, { method: 'delete' });
    }
  }

  return (
    <article className={classes.recipe}>
      <img src={recipe.image} alt={recipe.title} />
      <h1>{recipe.title}</h1>
      <time>{recipe.date}</time>
      <p>{recipe.description}</p>
      {token && (
        <menu className={classes.actions}>
          <Link to="edit">Edit</Link>
          <button onClick={startDeleteHandler}>Delete</button>
        </menu>
      )}
    </article>
  );
}

export default RecipeItem;
