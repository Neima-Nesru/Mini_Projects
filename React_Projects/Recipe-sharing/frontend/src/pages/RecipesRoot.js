import { Outlet } from 'react-router-dom';

import RecipesNavigation from '../components/RecipesNavigation';

function RecipesRootLayout() {
  return (
    <>
      <RecipesNavigation />
      <Outlet />
    </>
  );
}

export default RecipesRootLayout;
