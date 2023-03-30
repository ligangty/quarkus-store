import React, {useState, useEffect} from 'react';
import {Link, useParams} from 'react-router-dom';
import {APP_ROOT,PATH_TO_IMG_MAP} from '../../Constants';
import {jsonGet} from '../../../RestClient.js';


const initItems = productId => {
  const [items, setItems] = useState([]);
  const [product, setProduct] = useState({});
  let getUrl = `/api/items/byProduct?productId=${productId}`;
  useEffect(()=>{
    jsonGet({
      url: getUrl,
      done: response => {
        let raw = response;
        setItems(raw);
        setProduct(raw[0].product);
      },
      fail: errorText => {
        console.log(JSON.parse(errorText).error);
      }
    });
  }, [getUrl]);

  return [product, items];
};

export default function ShowItems() {
  const {productId} = useParams();
  const [product,items] = initItems(productId);
  return (
  <React.Fragment>
    <h2>Items for product: {product.name}</h2>
    <h3></h3>
    <table className="table">
      <tbody>
        {
          items.map(i=> <tr key={`tr-item-${i.id}`}>
              <td key={`td-item-${i.id}-1`}>
                <img key={`img-time-${i.id}`} src={PATH_TO_IMG_MAP[i.imagePath]} alt="Item" />
              </td>
              <td key={`td-item-${i.id}-2`}>
                <Link key={`to-item-${i.id}`} className="navbar-brand" to={`${APP_ROOT}/shopping/showitem?itemId=${i.id}`}>{i.name}</Link>
              </td>
              <td key={`td-item-${i.id}-3`}>{i.unitCost} $</td>
            </tr>)
        }
      </tbody>
    </table>
  </React.Fragment>
  );
}
