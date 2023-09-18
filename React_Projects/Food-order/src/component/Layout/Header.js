import React from 'react'
import banner from '../../assets/banner.jpg'
import classes from './Header.module.css'
import HeaderCartButton from './HeaderCartButton'

const Header = (props) => {
    return (
        <React.Fragment>
            <header className={classes.header}>
                <h1>MealBox</h1>
                <HeaderCartButton onClick={props.onShowCart} />
            </header>
            <div className={classes['main-image']}>
                <img src={banner} alt='meal banner' />

            </div>
        </React.Fragment>
    )
}

export default Header