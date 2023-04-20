import React from 'react';
import {getUser} from '../../UserUtils.js';

export default function ShowAccount() {
  const user = getUser();
  const address = user.homeAddress;
  return (
  <React.Fragment>
    <h2>Accounts</h2>
    <h3></h3>
    <div className="panel panel-default">
      <div className="panel-heading">
        <h3 className="panel-title">Personal information</h3>
      </div>
      <div className="panel-body" />
      <table>
        <tbody>
          <tr>
          <td>Loggin:</td>
          <td><input type="text" value={user.login} className="form-control" size="30" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>Firstname:</td>
          <td><input type="text" value={user.firstName} className="form-control" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>Lastname:</td>
          <td><input type="text" value={user.lastName} className="form-control" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>Email:</td>
          <td><input type="text" value={user.email} className="form-control" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>Telephone:</td>
          <td><input type="text" value={user.telephone} className="form-control" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>Birthday:</td>
          <td><input type="text" value={user.dateOfBirth} className="form-control" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>Age:</td>
          <td><input type="text" value={user.age} className="form-control" disabled="disabled" /></td>
          </tr>
        </tbody>
      </table>
    </div>
    <div className="panel panel-default">
      <div className="panel-heading">
          <h3 className="panel-title">Address</h3>
      </div>
      <div className="panel-body" />
      <table>
        <tbody>
          <tr>
          <td>Street1:</td>
          <td><input type="text" value={address.street1} className="form-control" size="36" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>City:</td>
          <td><input type="text" value={address.city} className="form-control" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>Zipcode:</td>
          <td><input type="text" value={address.zipcode} className="form-control" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>Country:</td>
          <td><input type="text" value={address.country.name} className="form-control" disabled="disabled" /></td>
          </tr>
        </tbody>
      </table>
    </div>
  </React.Fragment>
  );
}
