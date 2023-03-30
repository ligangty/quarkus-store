import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import NavHeader from './nav/NavHeader.jsx';
import NavFooter from './nav/NavFooter.jsx';
import BodyContainer from './content/Container.jsx';
import {APP_ROOT} from './Constants.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import './styles/app.css';
import './styles/sticky-footer-navbar.css';

// class DebugRouter extends BrowserRouter {
//   constructor(props){
//     super(props);
//     console.log('initial history is: ', JSON.stringify(this.history, null,2))
//     this.history.listen((location, action)=>{
//       console.log(
//         `The current URL is ${location.pathname}${location.search}${location.hash}`
//       )
//       console.log(`The last navigation action was ${action}`, JSON.stringify(this.history, null,2));
//     });
//   }
// }

const App = () => <React.Fragment>
    <BrowserRouter basename={APP_ROOT}>
      <NavHeader />
      <BodyContainer />
      <NavFooter />
    </BrowserRouter>
  </React.Fragment>;
export default App;
