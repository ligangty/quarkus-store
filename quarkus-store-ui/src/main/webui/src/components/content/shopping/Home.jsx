import React from 'react';
import {Outlet} from 'react-router';
import splash from '../../images/splash.gif';

export default class ShoppingHome extends React.Component {
  render(){
    // TODO: stats will be render based on the backend addons response, this is a mock;
    return (
    <React.Fragment>
      <h2></h2>
      <h3></h3>
      <div style={{textAlign: "center", marginTop: "50px"}}>
        <map name="pestoremap">
          <area alt="Birds" coords="72,2,280,250" shape="RECT" href={`#/shopping/showproducts/Birds`}/>
          <area alt="Fish" coords="2,180,72,250" shape="RECT" href={`#/shopping/showproducts/Fish`}/>
          <area alt="Dogs" coords="60,250,130,320" shape="RECT" href={`#/shopping/showproducts/Dogs`}/>
          <area alt="Reptiles" coords="140,270,210,340" shape="RECT"
                href={`#/shopping/showproducts/Reptiles`}/>
          <area alt="Cats" coords="225,240,295,310" shape="RECT" href={`#/shopping/showproducts/Cats`}/>
          <area alt="Birds" coords="280,180,350,250" shape="RECT" href={`#/shopping/showproducts/Birds`}/>
        </map>
        <img src={splash} border="0" width="350" height="355" align="middle" useMap="#pestoremap"
              alt="Welcome to YAPS PetStore"/>
      </div>
      <Outlet />
    </React.Fragment>
    );
  }
}
