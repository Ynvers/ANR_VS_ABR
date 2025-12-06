import matplotlib.pyplot as plt
import pandas as pd


df = pd.read_csv('resultats_arbres.csv')
fig, axs = plt.subplots(2, 2, figsize=(12, 8))

# ==========================================
# LIGNE 1 : CAS ALÉATOIRE (Échelle Normale)
# ==========================================

# 1. Construction (Haut-Gauche)
axs[0, 0].plot(df['N'], df['ABR_Const_Rand'], color='red', label='ABR')
axs[0, 0].plot(df['N'], df['ARN_Const_Rand'], color='blue', label='ARN')
axs[0, 0].set_title("Construction (Aléatoire)")
axs[0, 0].legend()
axs[0, 0].grid(alpha=0.2)

# 2. Recherche (Haut-Droite)
axs[0, 1].plot(df['N'], df['ABR_Search_Rand'], color='red', label='ABR')
axs[0, 1].plot(df['N'], df['ARN_Search_Rand'], color='blue', label='ARN')
axs[0, 1].set_title("Recherche (Aléatoire)")
axs[0, 1].grid(alpha=0.2)

# ==========================================
# LIGNE 2 : CAS TRIÉ (Échelle Logarithmique)
# ==========================================

# 3. Construction (Bas-Gauche)
axs[1, 0].plot(df['N'], df['ABR_Const_Sorted'], color='red', label='ABR')
axs[1, 0].plot(df['N'], df['ARN_Const_Sorted'], color='blue', label='ARN')
axs[1, 0].set_title("Construction (Données Triées)")
axs[1, 0].set_yscale('log')
axs[1, 0].grid(alpha=0.2)

# 4. Recherche (Bas-Droite)
axs[1, 1].plot(df['N'], df['ABR_Search_Sorted'], color='red', label='ABR')
axs[1, 1].plot(df['N'], df['ARN_Search_Sorted'], color='blue', label='ARN')
axs[1, 1].set_title("Recherche (Données Triées)")
axs[1, 1].set_yscale('log')
axs[1, 1].grid(alpha=0.2)

plt.tight_layout()
plt.savefig("graphique.png")
plt.show()