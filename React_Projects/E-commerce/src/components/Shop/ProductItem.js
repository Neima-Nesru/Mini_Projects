import { useDispatch } from 'react-redux';
import Card from '../UI/Card';
import classes from './ProductItem.module.css';
import { cartActions } from '../../store/cart-slice';

const ProductItem = (props) => {
  // const cart = useSelector(state => state.cart)

  const dispatch = useDispatch()

  const { id, title, price, description } = props;

  const addHandler = () => {

    //   const newTotalQuantity = cart.totalQuantity + 1
    //   const updatedItems = cart.items.slice()
    //   const existingItem = updatedItems.find(item => item.id === id)

    //   if (existingItem) {
    //     const updatedItem = { ...existingItem }
    //     updatedItem.quantity++
    //     updatedItem.totalPrice = updatedItem.totalPrice + price
    //     const existingItemIndex = updatedItems.findIndex(item => item.id === id)

    //     updatedItems[existingItemIndex] = updatedItem
    //   }

    //   else {
    //     updatedItems.push({
    //       id: id,
    //       price: price,
    //       quantity: 1,
    //       totalPrice: price,
    //       name: title
    //     })
    //   }

    //   const newCart = {
    //     totalQuantity: newTotalQuantity,
    //     items: updatedItems
    //   }

    //   dispatch(cartActions.replaceCart(newCart))
    dispatch(cartActions.addItem({
      id,
      title,
      price,
    }))
  }

  return (
    <li className={classes.item}>
      <Card>
        <header>
          <h3>{title}</h3>
        </header>
        <p>{description}</p>

        <div className={classes.price}>{price.toFixed(2)}ETB</div>

        <div className={classes.actions}>
          <button onClick={addHandler}>Add to Cart</button>
        </div>
      </Card>
    </li>
  );
};

export default ProductItem;
