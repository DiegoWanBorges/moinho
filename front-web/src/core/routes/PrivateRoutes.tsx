import { isAllowedByRole, isAuthenticated, Role } from "core/utils/auth";
import { Redirect, Route } from "react-router-dom";

type Props ={
    children: React.ReactNode;
    path: string;
    allowedRoutes?:Role[];
    exact?:boolean
}

const PrivateRoute = ({ children, path,allowedRoutes,exact  }: Props ) => {
     return (
      <Route
        exact={exact}
        path={path}
        render={({ location }) => {
          if ( !isAuthenticated() ){
            return(
              <Redirect
              to={{
                pathname: "/auth/login",
                state: { from: location }
              }}
            />
            )
          } else if(!isAllowedByRole(allowedRoutes) ){
            return (
              <Redirect to={{pathname: "/home/"}}/>
            )
          } 
          return children;
        }}
      />
    );
  }

  export default PrivateRoute;