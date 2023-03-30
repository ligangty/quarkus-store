import React from 'react';
import {Link} from 'react-router-dom';
import PropTypes from 'prop-types';
import {Utils} from '../CompUtils.js';
import {APP_ROOT} from "../Constants.js";

const LocalURLSection = ({storeKey}) => <div className="left-half">
    <label>Local URL:</label>{' '}
    <a href={Utils.storeHref(storeKey)} target="_new">{Utils.storeHref(storeKey)}</a>
  </div>;

LocalURLSection.propTypes = {
  storeKey: PropTypes.string
};

const CapabilitiesSection = ({options}) => <div className="left-half">
    <label>Capabilities:</label>{' '}
    {
      options.map(option => <div key={option.title} className="options">
          <span className="key">{option.icon} </span>
        </div>)
    }
  </div>;

CapabilitiesSection.propTypes = {
  options: PropTypes.array
};

const StoreNameSection = ({store, storeClass}) => <div className="fieldset-caption">
    <Link to={`${APP_ROOT}/${store.type}/${store.packageType}/view/${store.name}`}>
      <span className={storeClass}>{store.packageType}-{store.name}</span>
    </Link>
  </div>;

StoreNameSection.propTypes = {
  store: PropTypes.object,
  storeClass: PropTypes.string
};


export {LocalURLSection, CapabilitiesSection, StoreNameSection};
