import React from 'react';

export default class NavFooter extends React.Component {
  render(){
    // TODO: stats will be render based on the backend addons response, this is a mock;
    return (
      <div id="footer">
          <div className="container">
              <div className="col-md-2 centered-text">
                  <a href="https://github.com/ligangty/quarkus-store">Source code on GitHub</a>
                  <br />
              </div>
              <div className="col-md-8"></div>
              <div className="col-md-2 centered-text">
                  <a href="/q/swagger-ui/#">Swagger Contract</a>
                  <br />Conversation []
              </div>
          </div>
      </div>

    );
  }
}
