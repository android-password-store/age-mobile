package ageMobile

import (
	"bytes"
	"io"

	"filippo.io/age"
)

type Keypair struct {
	PrivateKey string
	PublicKey  string
}

func Encrypt(in []byte, publicKey string) ([]byte, error) {
	recipient, err := age.ParseX25519Recipient(publicKey)
	if err != nil {
		return nil, err
	}

	out := &bytes.Buffer{}
	w, err := age.Encrypt(out, recipient)
	if err != nil {
		return nil, err
	}

	if _, err := w.Write(in); err != nil {
		return nil, err
	}

	if err := w.Close(); err != nil {
		return nil, err
	}

	return out.Bytes(), nil
}

func Decrypt(in []byte, privateKey string) ([]byte, error) {
	identity, err := age.ParseX25519Identity(privateKey)
	if err != nil {
		return nil, err
	}

	out := &bytes.Buffer{}

	r, err := age.Decrypt(bytes.NewBuffer(in), identity)
	if err != nil {
		return nil, err
	}

	if _, err := io.Copy(out, r); err != nil {
		return nil, err
	}

	return out.Bytes(), nil
}

func GenerateKeypair() (*Keypair, error) {
	identity, err := age.GenerateX25519Identity()
	if err != nil {
		return nil, err
	}

	return &Keypair{
		PrivateKey: identity.Recipient().String(),
		PublicKey:  identity.String(),
	}, nil
}
