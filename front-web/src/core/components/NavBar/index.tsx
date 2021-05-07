import { useEffect, useState } from 'react';
import { Link, NavLink, useLocation } from 'react-router-dom';
import './styles.scss';
import menu from 'core/assets/images/menu.svg';
import { getAccessTokenDecoded, logout } from 'core/utils/auth';

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
            <NavLink className="nav-link" to="/registrations" exact onClick={() => setDrawerActive(false)}>
              CADASTROS
            </NavLink>
          </li>
          <li>
            <NavLink className="nav-link" to="/formulation" onClick={() => setDrawerActive(false)}>
              FORMULAÇÃO
            </NavLink>
          </li>
          <li>
            <NavLink className="nav-link" to="/production" onClick={() => setDrawerActive(false)} >
              PRODUÇÃO
            </NavLink>
          </li>
          <li>
            <NavLink className="nav-link" to="/stock" onClick={() => setDrawerActive(false)} >
              ESTOQUE
            </NavLink>
          </li>
          <li>
            <NavLink className="nav-link" to="/payment" onClick={() => setDrawerActive(false)} >
              PAGAMENTOS
            </NavLink>
          </li>
          <li>
            <NavLink className="nav-link" to="/calculation" onClick={() => setDrawerActive(false)} >
              APURAÇÃO
            </NavLink>
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
