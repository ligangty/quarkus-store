import React from 'react';
import {Route, Routes} from 'react-router-dom';
import ShoppingMain from './shopping/Main.jsx';
import ShoppingNav from './shopping/ShoppingNav.jsx';
import ShoppingHome from './shopping/Home.jsx';
import ShowProducts from './shopping/ShowProducts.jsx';
import ShowItems from './shopping/ShowItems.jsx';
import ShowItem from './shopping/ShowItem.jsx';
import ShowAccount from './shopping/ShowAccount.jsx';
import SearchResults from './shopping/SearchResults.jsx';
import CRUDNav from './admin/CRUDNav.jsx';

const ErrorMsgBox = () => <div className="page-header">
    { /* Error messages */ }
  </div>;

const BodyContentPlaceHolder = () => <div className="col-md-1"></div>;

const BodyContainer = () => <div className="container">
    {
     // browseCompatible
    }
    <ErrorMsgBox />
    <Routes>
      <Route exact path={`/`} element={<ShoppingNav />} />
      <Route path={`shopping/*`} element={<ShoppingNav />} />
      <Route path={`admin/*`} element={<CRUDNav />} />
    </Routes>
    <BodyContentPlaceHolder />
    <div className="col-md-9 well">
    <Routes>
      <Route exact path={`/`} element={<ShoppingMain />}>
        <Route exact path={``} element={<ShoppingHome />} />
        <Route path={`shopping/showproducts/:categoryName`} element={<ShowProducts />} />
        <Route path={`shopping/showitems/:productId`} element={<ShowItems />} />
        <Route path={`shopping/showitem`} element={<ShowItem />} />
        <Route path={`shopping/showaccount`} element={<ShowAccount />} />
        <Route path={`shopping/searchresult`} element={<SearchResults />} />
      </Route>
    </Routes>
    </div>
  </div>;

export default BodyContainer;
