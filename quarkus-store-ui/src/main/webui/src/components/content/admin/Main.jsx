import React from 'react';
import {Route, Routes} from 'react-router-dom';
import {APP_ROOT} from '../../Constants.js';
import Category from './category/Category.jsx';

export default class ShoppingMain extends React.Component {
  render(){
    return (
      <div className="col-md-9 well">
        <React.Fragment>
          <Routes>
            <Route exact path={[`${APP_ROOT}/admin/category/search`]} element={<Category />} />
          </Routes>
        </React.Fragment>
      </div>
    );
  }
}
