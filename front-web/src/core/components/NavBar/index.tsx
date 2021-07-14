import { useEffect, useState } from 'react';
import * as FaIcons from 'react-icons/fa';
import * as AiIcons from 'react-icons/ai';
import * as GiIcons from 'react-icons/gi';
import * as MdIcons from 'react-icons/md';
import * as BsIcons from 'react-icons/bs';
import { Link, useLocation } from 'react-router-dom';
import './styles.scss';
import { IconContext } from 'react-icons';
import { getAccessTokenDecoded, isAllowedByRole, isTokenValid, logout } from 'core/utils/auth';


const Navbar = () => { 
  const [currentUser, setCurrentUser] = useState('');
  const [sidebar, setSidebar] = useState(false);
  const showSidebar = () => setSidebar(!sidebar);
  const location = useLocation();

  useEffect(() => {
    const currenUserData = getAccessTokenDecoded();
    setCurrentUser(currenUserData.user_name);
  }, [location, sidebar])

  return (

    <IconContext.Provider value={{ color: '#fff' }}>

      <div className='navbar'>
        <div className="navbar-left">
          {isTokenValid() ? (
            <>
              <Link to='#' className='menu-bars'>
                <FaIcons.FaBars onClick={showSidebar} />
              </Link>
            </>
          ) : (
            <h3 className="navbar-left-title"> Moinho</h3>
          )}
        </div>
        <div className="navbar-right">
          {isTokenValid() ? (
            <>
              <AiIcons.AiOutlineLogout
                className="navbar-right-icon"
                onClick={() => { logout(); setSidebar(false) }}
              />
              <h6 className="navbar-right-title">{currentUser}</h6>
            </>
          ) : (null)}
        </div>

      </div>

      <nav className={(sidebar && isTokenValid) ? 'nav-menu active' : 'nav-menu'}>
        <ul className='nav-menu-items' onClick={showSidebar}>
          <li className='navbar-toggle'>
            <Link to='#' className='menu-bars'>
              <AiIcons.AiOutlineClose />
            </Link>
          </li>

          <li className="nav-text">
            <Link to="/home">
              <AiIcons.AiFillHome />
              <span>Home</span>
            </Link>
          </li>

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
            "ROLE_REGISTRATION_STATUSPALLET"]) ? (
            <li className="nav-text">
              <Link to="/registrations">
                <FaIcons.FaRegIdCard />
                <span>Cadastro</span>
              </Link>
            </li>
          ) : null}

          {isAllowedByRole(["ROLE_ADMIN", "ROLE_FORMULATION"]) ? (
            <li className="nav-text">
              <Link to="/formulations">
                <GiIcons.GiChemicalDrop />
                <span>Formulação</span>
              </Link>
            </li>
          ) : null}

          {isAllowedByRole(["ROLE_ADMIN", "ROLE_PRODUCTION"]) ? (
            <li className="nav-text">
              <Link to="/productions">
                <GiIcons.GiFactory />
                <span>Produção</span>
              </Link>
            </li>
          ) : null}

          {isAllowedByRole(["ROLE_ADMIN", "ROLE_STOCK"]) ? (
            <li className="nav-text">
              <Link to="/stock">
                <GiIcons.GiForklift />
                <span>Estoque</span>
              </Link>
            </li>
          ) : null}

          {isAllowedByRole(["ROLE_ADMIN", "ROLE_LABOR_PAYMENT", "ROLE_OPERATIONAL_PAYMENT"]) ? (
            <li className="nav-text">
              <Link to="/payments">
                <MdIcons.MdPayment />
                <span>Pagamentos</span>
              </Link>
            </li>
          ) : null}

          {isAllowedByRole(["ROLE_ADMIN", "ROLE_COST_CALCULATION"]) ? (
            <li className="nav-text">
              <Link to="/calculations">
                <BsIcons.BsGraphUp />
                <span>Apuração</span>
              </Link>
            </li>
          ) : null}

          <li className="nav-text">
            <Link to="#"
              onClick={() => { logout(); setSidebar(false) }}
            >
              <AiIcons.AiOutlineLogout className="logout-icon" />
              <span>Sair</span>
            </Link>
          </li>

        </ul>
      </nav>

    </IconContext.Provider>

  );
}

export default Navbar;
