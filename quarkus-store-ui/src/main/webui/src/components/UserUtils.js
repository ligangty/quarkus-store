import {useState, useEffect} from 'react';
import {useLocation} from 'react-router-dom';
import {jsonGet} from '../RestClient.js';

export const getUser = () => {
  const location = useLocation();
  let user = location.state;
  if (!user||!user.login){
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
