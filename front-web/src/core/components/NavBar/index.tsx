import { useEffect, useState } from 'react';
import { Link, NavLink, useLocation } from 'react-router-dom';
import './styles.scss';
import menu from 'core/assets/images/menu.svg';
import { getAccessTokenDecoded, isAllowedByRole, logout } from 'core/utils/auth';


function NavBar() {
  const [drawerActive, setDrawerActive] = useState(false);
  const [currentUser, setCurrentUser] = useState('');
  const location = useLocation();


  useEffect(() => {
    const currenUserData = getAccessTokenDecoded();
    setCurrentUser(currenUserData.user_name);
  }, [location])

  return (
    <nav className="nav-main">

      <Link to="/home" className="nav-title-text">
        <h4>Moinho</h4>
      </Link>
      {currentUser && (
        <button
          className="menu-mobile-btn"
          type="button"
          onClick={() => setDrawerActive(!drawerActive)}
        >
          <img src={menu} alt="mobile menu" />
        </button>)}

      <div className={drawerActive ? "menu-mobile-container" : "menu-container"}>
        {currentUser && (<ul className="main-menu">
          <li >
          {isAllowedByRole(["ROLE_ADMIN", 
                            "ROLE_REGISTRATION_PARAMETER",
                            "ROLE_REGISTRATION_USER",
                            "ROLE_REGISTRATION_GROUP",
                            "ROLE_REGISTRATION_UNITY",
                            "ROLE_REGISTRATION_PRODUCT",
                            "ROLE_REGISTRATION_SECTOR",
                            "ROLE_REGISTRATION_EMPLOYEE",
                            "ROLE_REGISTRATION_LABORCOSTTYPE",
                            "ROLE_REGISTRATION_OPERATIONALCOSTTYPE",
                            "ROLE_REGISTRATION_OCCURRENCE",
                            "ROLE_REGISTRATION_STATUSPALLET"]) ?
            (<NavLink className="nav-link" to="/registrations" onClick={() => setDrawerActive(false)}>
              CADASTROS
            </NavLink>) : null}
          </li>
          <li>
            {isAllowedByRole(["ROLE_ADMIN", "ROLE_FORMULATION"]) ?
              (<NavLink className="nav-link" to="/formulations" onClick={() => setDrawerActive(false)}>
                FORMULAÇÃO
              </NavLink>) : null}
          </li>
          <li>
            {isAllowedByRole(["ROLE_ADMIN", "ROLE_PRODUCTION"]) ?
              (<NavLink className="nav-link" to="/productions" onClick={() => setDrawerActive(false)} >
                PRODUÇÃO
              </NavLink>) : null}
          </li>
          <li>
            {isAllowedByRole(["ROLE_ADMIN", "ROLE_STOCK"]) ?
              (<NavLink className="nav-link" to="/stock" onClick={() => setDrawerActive(false)} >
                ESTOQUE
              </NavLink>) : null}
          </li>
          <li>
            {isAllowedByRole(["ROLE_ADMIN", "ROLE_LABOR_PAYMENT", "ROLE_OPERATIONAL_PAYMENT"]) ?
              (<NavLink className="nav-link" to="/payments" onClick={() => setDrawerActive(false)} >
                PAGAMENTOS
              </NavLink>) : null}
          </li>
          <li>
            {isAllowedByRole(["ROLE_ADMIN", "ROLE_COST_CALCULATION"]) ?
              (<NavLink className="nav-link" to="/calculations" onClick={() => setDrawerActive(false)} >
                APURAÇÃO
              </NavLink>) : null}
          </li>
          <li>
            <NavLink className="nav-link" to="" onClick={() => logout()} >
              SAIR
            </NavLink>
          </li>
        </ul>)}

      </div>
    </nav>
  );
}
export default NavBar;
