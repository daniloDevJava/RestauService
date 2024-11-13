# fichier: fonction.py
import cv2
from pyzbar.pyzbar import decode

def scan_qr_code():
    # Ouvrir la caméra
    import cv2
    cap = cv2.VideoCapture(0)  # Essaye aussi 1, 2 si 0 ne fonctionne pas
    if not cap.isOpened():
        print("Erreur d'accès à la caméra")
    else:
        print("Caméra ouverte avec succès")

    text = None
    
    while True:
        # Lire l'image de la caméra
        ret, frame = cap.read()
        if not ret:
            break
        
        # Décoder les QR codes dans l'image
        qr_codes = decode(frame)
        for qr in qr_codes:
            # Extraire les données du QR code
            text = qr.data.decode("utf-8")
            cap.release()  # Libérer la caméra
            cv2.destroyAllWindows()  # Fermer les fenêtres ouvertes par OpenCV
            return text  # Retourner le texte dès qu'il est trouvé

        # Afficher le flux vidéo
        cv2.imshow("QR Code Scanner", frame)

        # Quitter avec la touche 'q'
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    cap.release()
    cv2.destroyAllWindows()
    return None
