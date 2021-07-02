import { NavLink } from 'react-router-dom';
import './styles.scss'


const NavBarCalculation = () => (
    <nav className="nav-registration-container">
        <ul className="nav-registration-list">
            <li>
                <NavLink to="/calculations/registrations" className="nav-registration-item">Cadastro</NavLink>
            </li>
        </ul>
    </nav>
)

export default NavBarCalculation;