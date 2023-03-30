import React, {useState, useEffect} from 'react';
import {Link, useParams} from 'react-router-dom';
import {APP_ROOT} from '../../Constants.js';
import {jsonGet} from '../../../RestClient.js';

const initProducts = categoryName => {
  const [products, setProducts] = useState([]);
  let getUrl = `/api/products/byCategory?category=${categoryName}`;
  useEffect(()=>{
    jsonGet({
      url: getUrl,
      done: response => {
        let raw = response;
        setProducts(raw);
      },
      fail: errorText => {
        console.log(JSON.parse(errorText).error);
      }
    });
  }, [getUrl]);

  return products;
};

export default function ShowProducts() {
  const {categoryName} = useParams();
  const products = initProducts(categoryName);

  return (
  <React.Fragment>
    <h2>Products for category: {categoryName}</h2>
    <h3></h3>
    <table className="table">
      <tbody>
        {
          products.map(i=> <tr key={`tr-item-${i.id}`}>
              <td key={`td-item-${i.id}-1`}>
                <Link key={`to-item-${i.id}`} className="navbar-brand" to={`${APP_ROOT}/shopping/showitems/${i.id}`}>{i.name}</Link>
              </td>
              <td key={`td-item-${i.id}-2`}>{i.description}</td>
            </tr>)
        }
      </tbody>
    </table>
  </React.Fragment>
  );
}
