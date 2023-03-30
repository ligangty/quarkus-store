import React, {useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import {APP_ROOT} from '../Constants.js';

// mock user login
// const isUserloggedIn = true;
// const username = "mock";

const SearchForm = function(){
  const [content, setContent]= useState("");
  const navigate = useNavigate();
  const onSubmitHandler = event => {
    event.preventDefault();
    navigate(`/shopping/searchresult?keyword=${content}`);
  };
  const contentChangeHandler = (setFunction, event) => {
    setFunction(event.target.value);
  };
  return (
    <form onSubmit={onSubmitHandler}>
      <div className="navbar-form navbar-left" role="search">
        <div className="form-group">
          <input type="text" className="form-control" onChange={e => contentChangeHandler(setContent, e)} />
        </div>
        <input type="submit" value="Search" className="btn btn-default" />
      </div>
    </form>
  );
};

export default function NavHeader() {
  // eslint-disable-next-line max-lines-per-function
  // addons will be render based on the backend addons response, this is a mock;
  return (
    <div className="navbar navbar-default navbar-fixed-top" role="navigation">
      <div className="container">
        <div className="navbar-header">
          <button type="button" className="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span className="sr-only">Toggle navigation</span>
            <span className="icon-bar"></span>
            <span className="icon-bar"></span>
            <span className="icon-bar"></span>
          </button>
          <Link key="to-home" className="navbar-brand" to={`${APP_ROOT}`}>Yaps Petstore EE 7</Link>
        </div>

        <div className="collapse navbar-collapse">
          <ul className="nav navbar-nav">
            <li>
              <Link key="to-home" className="navbar-brand" to={`${APP_ROOT}/admin/category/search`}>Admin</Link>
            </li>
          </ul>
          <ul className="nav navbar-nav navbar-right">
              <li></li>
              <li></li>
              <li>
                <a href="/applicationPetstore/shopping/signon.xhtml">Log in<i className="fa fa-sign-in" aria-hidden="true"></i></a>
              </li>
              <li></li>
              <li>
                <SearchForm />
              </li>
          </ul>
        </div>
      </div>
    </div>
  );
}
