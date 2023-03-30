import React from 'react';
import {Link} from 'react-router-dom';
import {APP_ROOT} from '../../Constants.js';

export default function ShoppingNav() {
  // TODO: stats will be render based on the backend addons response, this is a mock;
  return (
    <div className="col-md-2 well">
      <ul className="nav nav-list">
        <li>
          <h3><Link key="to-shopping-fish" to={`${APP_ROOT}/shopping/showproducts/Fish`}>Fish</Link></h3>
        </li>
        <li>
          <h3><Link key="to-shopping-dogs" to={`${APP_ROOT}/shopping/showproducts/Dogs`}>Dogs</Link></h3>
        </li>
        <li>
          <h3><Link key="to-shopping-reptiles" to={`${APP_ROOT}/shopping/showproducts/Reptiles`}>Reptiles</Link></h3>
        </li>
        <li>
          <h3><Link key="to-shopping-cats" to={`${APP_ROOT}/shopping/showproducts/Cats`}>Cats</Link></h3>
        </li>
        <li>
          <h3><Link key="to-shopping-birds" to={`${APP_ROOT}/shopping/showproducts/Birds`}>Birds</Link></h3>
        </li>
      </ul>
    </div>
  );
}
