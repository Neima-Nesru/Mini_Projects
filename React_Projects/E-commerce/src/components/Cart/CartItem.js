import { useDispatch } from 'react-redux';
import classes from './CartItem.module.css';
import { cartActions } from '../../store/cart-slice';

const CartItem = (props) => {

  const dispatch = useDispatch()

  const { id, title, quantity, total, price } = props.item;

  const addHandler = () => {
    dispatch(cartActions.addItem({
      id,
      title,
      price
    }))
  }

  const removeHandler = () => {
    dispatch(cartActions.removeItem(id))
  }

  return (
    <li className={classes.item}>
      <header>
        <h3>{title}</h3>
        <div className={classes.price}>
          {total.toFixed(2)}{' ETB '}
          <span className={classes.itemprice}>({price.toFixed(2)} ETB/item)</span>
        </div>
      </header>
      <div className={classes.details}>
        <div className={classes.quantity}>
          x <span>{quantity}</span>
        </div>
        <div className={classes.actions}>
          <button onClick={removeHandler}>-</button>
          <button onClick={addHandler}>+</button>
        </div>
      </div>
    </li>
  );
};

export default CartItem;
