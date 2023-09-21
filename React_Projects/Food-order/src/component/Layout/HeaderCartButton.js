import { useContext, useEffect, useState } from 'react';

import CartIcon from '../Cart/CartIcon';
import CartContext from '../../store/cart-context';
import classes from './HeaderCartButton.module.css';

const HeaderCartButton = (props) => {
    const [btnIsHighlighted, setBtnIsHighlighted] = useState(true)
    const cartCtx = useContext(CartContext);

    const { items } = cartCtx /* to get only the items from the cart context not the entire context */

    const numberOfCartItems = cartCtx.items.reduce((curNumber, item) => {
        return curNumber + item.amount;
    }, 0);

    const btnClasses = `${classes.button} ${btnIsHighlighted ? classes.bump : ''}`
    useEffect(() => {
        if (items.length === 0) {
            return;
        }
        setBtnIsHighlighted(false)

        const timer = setTimeout(() => {
            setBtnIsHighlighted(true)
        }, 300);

        return () => {
            clearTimeout(timer)
        }
    }, [items])
    return (
        <button className={btnClasses} onClick={props.onClick}>
            <span className={classes.icon}>
                <CartIcon />
            </span>
            <span>Your Cart</span>
            <span className={classes.badge}>{numberOfCartItems}</span>
        </button>
    );
};

export default HeaderCartButton;