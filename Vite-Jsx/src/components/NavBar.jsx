import { FaHome, FaPiggyBank, FaTimes, FaUser } from "react-icons/fa";
import { Link } from "react-router-dom";
import "./NavBar.css";

const NavBar = () => {

    const handleLogout = async () => {
        try {
            const response = await fetch('/req/logout', { method: 'GET', credentials: 'include' });
            if (response.ok) {
                window.location.href = "http://localhost:8080/req/login";
            } else {
                console.error("Logout failed");
            }
        } catch (error) {
            console.error("Logout error", error);
        }
    };


    return (
        <header className="navbar">
            <nav>
                <div className="logo">
                    <h1>&nbsp;EPUB📖SPOT</h1>
                </div>

                <div>
                <ul className="nav-links">
                    <li>
                        <Link to="/">
                            <span className="text-nav">Home</span>
                            <FaHome className="icon-nav" />
                        </Link>
                    </li>
                    <li>
                        <Link to="/account">
                            <span className="text-nav">Account</span>
                            <FaUser className="icon-nav" />
                        </Link>
                    </li>
                    <li>
                        <Link to="/card">
                            <span className="text-nav">Card</span>
                            <FaPiggyBank className="icon-nav" />
                        </Link>
                    </li>
                    <li>
                        <button onClick={handleLogout} className="logout-button">
                            <span className="text-nav">Logout</span>
                            <FaTimes className="icon-logout" />
                        </button>
                    </li>
                </ul>
                </div>
            </nav>
        </header>
    );
};

export default NavBar;