import { useEffect } from 'react';
import { useFetcher } from 'react-router-dom';

import classes from './Search.module.css';

function Search() {
  const fetcher = useFetcher();
  const { data, state } = fetcher;

  useEffect(() => {
    if (state === 'idle' && data && data.message) {
      window.alert(data.message);
    }
  }, [data, state]);

  return (
    <fetcher.Form
      method="post"
      action="/newsletter"
      className={classes.newsletter}
    >
      <input
        type="email"
        placeholder="Search for Recipes..."
        aria-label="Search for Recipes"
      />
      <button>Search</button>
    </fetcher.Form>
  );
}

export default Search;
