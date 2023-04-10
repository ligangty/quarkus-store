import React, {useState, useEffect} from 'react';
import {useLocation} from 'react-router-dom';
import {jsonGet} from '../../../RestClient.js';

const getUser = () => {
  const location = useLocation();
  let user = location.state;
  if (!user){
    const [userFetch, setUserFetch] = useState("");
    let getUrl = `/api/users/loggedIn`;
    useEffect(()=>{
      jsonGet({
        url: getUrl,
        done: response => {
          let raw = response;
          setUserFetch(raw);
        },
        fail: errorText => {
          console.log(JSON.parse(errorText).error);
        }
      });
    }, []);
    user = userFetch;
  }

  return user;
};

export default function ShowAccount() {
  const user = getUser();
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
          <td><input type="text" name="j_idt71:j_idt83" value="+1 786 1212 987" className="form-control" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>Date Of Birth:</td>
          <td><input type="text" name="j_idt71:j_idt85" value="12/04/1923" className="form-control" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>Age:</td>
          <td><input type="text" name="j_idt71:j_idt87" value="99" className="form-control" disabled="disabled" /></td>
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
          <td><input type="text" name="j_idt71:j_idt91" value="543 Sun Set Boulevard" className="form-control" size="36" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>Street2:</td>
          <td><input type="text" name="j_idt71:j_idt93" className="form-control" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>City:</td>
          <td><input type="text" name="j_idt71:j_idt95" value="Black Star" className="form-control" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>State:</td>
          <td><input type="text" name="j_idt71:j_idt97" className="form-control" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>Zipcode:</td>
          <td><input type="text" name="j_idt71:j_idt99" value="9999" className="form-control" disabled="disabled" /></td>
          </tr>
          <tr>
          <td>Country:</td>
          <td><input type="text" name="j_idt71:j_idt101" value="Zimbabwe" className="form-control" disabled="disabled" /></td>
          </tr>
        </tbody>
      </table>
    </div>
  </React.Fragment>
  );
}
