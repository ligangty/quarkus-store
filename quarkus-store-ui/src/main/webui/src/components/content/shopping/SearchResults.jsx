import React, {useState, useEffect} from 'react';
import {useSearchParams, Link} from 'react-router-dom';
import {APP_ROOT,PATH_TO_IMG_MAP} from '../../Constants.js';
import {jsonGet} from '../../../RestClient.js';

const searchItems = keyword => {
  const [items, setItems] = useState({});
  let getUrl = `/api/items/byKeyword?keyword=${keyword}`;
  useEffect(()=>{
    jsonGet({
      url: getUrl,
      done: response => {
        let raw = response;
        setItems(raw);
      },
      fail: errorText => {
        console.log(JSON.parse(errorText).error);
      }
    });
  }, [getUrl]);

  return items;
};
export default function SearchResults() {
  const [searchParams] = useSearchParams();
  const keyword = searchParams.get("keyword");
  const items = searchItems(keyword);

  let contentTable = <h3>No Items Found</h3>;
  if(items && Object.keys(items).length !== 0){
    contentTable=
      <React.Fragment>
        <h3 />
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
                  <td key={`td-item-${i.id}-3`}>
                    <em>
                      {`${i.product.category.name} - ${i.product.name}`}
                    </em>
                  </td>
                  <td key={`td-item-${i.id}-4`}>{i.price}$</td>
                </tr>)
            }
          </tbody>
        </table>
      </React.Fragment>;
  }
  return (
    <React.Fragment>
      <h2>Search for : {keyword}</h2>
      {contentTable}
    </React.Fragment>
  );
}
