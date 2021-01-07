package mouvement;

import dessin.DessinFace;

/**
 * Rotation est la classe de gestion des Mouvements de Rotation sur le mod�le.
 * <p> Une Rotation est caract�ris�e par les informations suivantes :
 * <ul><li>Un tableau � deux dimensions rotAxe, suceptible d'�tre chang�.
 * <p>(Il sera initialis� en une matrice de rotation x, y ou z).</p></li>
 * <li>Un double PI, unique attribu� d�finitivement.</li>
 * <li>Un Facteur de Rotation unique attribu� d�finitivement � {@value ROTATION#FACTEURROTATION}.</li></ul>
 * De plus, Rotation h�rite de la classe Mouvement.</p>
 * @see Mouvement
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Math�o Gallego
 */
public class Rotation extends Mouvement{

	/**
	 * La matrice de rotation axiale x, y ou z.
	 */
	private float[][] rotAxe;
	/**
	 * La valeur de PI.
	 */
	public final static double PI = Math.PI;
	/**
	 * Le Facteur de Rotation.
	 * <p>Il servira au Constructeur pour calculer les valeurs de la matrice {@link Rotation#rotAxe}.</p>
	 */
	public final static int FACTEURROTATION = 25;

	/**
	 * Constructeur Rotation.
     * <p>La construction d'un objet Rotation appelle le constructeur de la classe dont il h�rite : Mouvement(DessinFace).</p>
	 * @param df
	 * 				Le dessinateur des Faces.
	 * @param axeDeRotation
	 * 				Le caract�re d�finissant l'axe de Rotation.
	 * @param facteurRotation
	 * 				Le facteur de Rotation.
	 * @see Mouvement#df
	 */
	public Rotation(DessinFace df,char axeDeRotation, double facteurRotation) {
		super(df);
		if(facteurRotation > 0) {
			if(axeDeRotation == 'x') {
				rotAxe = new float[][]{{1,0,0,0}, {0,(float) Math.cos(PI/facteurRotation),(float) - Math.sin(PI/facteurRotation),0},{0,(float) Math.sin(PI/facteurRotation),(float) Math.cos(PI/facteurRotation),0},{0,0,0,1}};
			}else if(axeDeRotation == 'y') {
				rotAxe = new float[][]{{(float)Math.cos(PI/facteurRotation),0,(float)-Math.sin(PI/facteurRotation),0},{0,1,0,0},{(float)Math.sin(PI/facteurRotation),0,(float)Math.cos(PI/facteurRotation),0},{0,0,0,1}};
			}else if(axeDeRotation == 'z'){
				rotAxe = new float[][]{{(float)Math.cos(PI/facteurRotation),(float)-Math.sin(PI/facteurRotation),0,0},{(float)Math.sin(PI/facteurRotation),(float)Math.cos(PI/facteurRotation),0,0},{0,0,1,0},{0,0,0,1}};
			}
		}
	}
	/**
     * Constructeur Rotation.
     * <p>La construction d'un objet Rotation(DessinFace, char) appelle son constructeur homonyme.</p>
	 * @param df
	 * 				Le dessinateur des Faces.
	 * @param axeDeRotation
	 * 				Le caract�re d�finissant l'axe de Rotation.
	 * @see Rotation#Rotation(DessinFace, char, double)
	 * @see Rotation#FACTEURROTATION
	 * @see Mouvement#df
	 */
	public Rotation(DessinFace df, char axeDeRotation) {
		this(df, axeDeRotation, FACTEURROTATION);
	}
	
	/**
	 * Effectue le mouvement de Rotation sur le mod�le.
	 * <p>En effectuant sur l'ensemble des sommets du mod�le la matrice de rotation {@link Rotation#rotAxe}</p>
	 * @param model
	 * 				La liste des sommets.
	 */
	public void effectuerMouvement(float[][] model) {
		float[][] res = new float[model.length][model[0].length];
		for(int i = 0; i < model.length; i++) {
			for(int j = 0; j < model[0].length; j++) {
				res[i][j] = rotAxe[i][0] * model[0][j] + rotAxe[i][1] * model[1][j] + rotAxe[i][2] * model[2][j] + rotAxe[i][3] * model[3][j];
			}
		}
		for(int i = 0; i < model.length; i++) {
			for(int j = 0; j < model[0].length; j++) {
				model[i][j] = res[i][j];
			}
		}
	}
}