import React from 'react';

export default class CRUDNav extends React.Component {
  render(){
    // TODO: stats will be render based on the backend addons response, this is a mock;
    return (
      <div className="col-md-2 well">
        <ul className="nav nav-list">
          <li><h3><a href="/applicationPetstore/admin/category/search.xhtml">Category</a></h3></li>
          <li><h3><a href="/applicationPetstore/admin/country/search.xhtml">Country</a></h3></li>
          <li><h3><a href="/applicationPetstore/admin/customer/search.xhtml">Customer</a></h3></li>
          <li><h3><a href="/applicationPetstore/admin/item/search.xhtml">Item</a></h3></li>
          <li><h3><a href="/applicationPetstore/admin/product/search.xhtml">Product</a></h3></li>
          <li><h3><a href="/applicationPetstore/admin/purchaseOrder/search.xhtml">Order</a></h3></li>
        </ul>
      </div>
    );
  }
}
