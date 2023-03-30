import React from 'react';
import {Outlet} from 'react-router-dom';

export default function ShoppingMain() {
  return (
    <React.Fragment>
      <Outlet />
    </React.Fragment>
  );
}
