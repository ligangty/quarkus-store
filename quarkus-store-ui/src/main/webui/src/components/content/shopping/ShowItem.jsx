import React, {useState, useEffect} from 'react';
import {useSearchParams} from 'react-router-dom';
import {PATH_TO_IMG_MAP} from '../../Constants.js';
import {jsonGet} from '../../../RestClient.js';

const initItem = itemId => {
  const [item, setItem] = useState({product: {}});
  let getUrl = `/api/items/${itemId}`;
  useEffect(()=>{
    jsonGet({
      url: getUrl,
      done: response => {
        let raw = response;
        setItem(raw);
      },
      fail: errorText => {
        console.log(JSON.parse(errorText).error);
      }
    });
  }, [getUrl]);

  return item;
};

export default function ShowItem() {
  const [searchParams] = useSearchParams();
  const itemId = searchParams.get("itemId");
  const item = initItem(itemId);

  return (
  <React.Fragment>
    <h2>{item.product.name} - {item.name}</h2>
    <h3></h3>
    <table className="table">
      <tbody>
      <tr key={`tr-item-${item.id}`}>
        <td key={`td-item-${item.id}-1`}>
          <img key={`img-time-${item.id}`} src={PATH_TO_IMG_MAP[item.imagePath]} alt="Item" />
        </td>
        <td key={`td-item-${item.id}-2`}>
          Unit cost:{item.unitCost}$
        </td>
        <td key={`td-item-${item.id}-3`}></td>
      </tr>
      </tbody>
    </table>
    <table>
      <tbody>
        <tr>
          <td>
            {item.description}
          </td>
        </tr>
      </tbody>
    </table>
  </React.Fragment>
  );
}
